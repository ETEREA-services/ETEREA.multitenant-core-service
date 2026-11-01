package eterea.tenant.service.modules.contable.balance.infrastructure.service;

import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;
import eterea.tenant.service.modules.contable.balance.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.CotizacionOnDemandPort;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.MonedaQueryPort;
import eterea.tenant.service.modules.contable.balance.infrastructure.client.BcraCotizacionClient;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Obtiene y cachea las tasas que faltan, sólo para el par/período consultado.
 *
 * <p>No hay scheduler: el reporte llama a este servicio cuando
 * {@code findFechasSinCotizacion} devuelve fechas. El upsert es idempotente y
 * la escritura se limita a {@code moneda_cotizacion}; nunca toca el libro.</p>
 */
@Service
@RequiredArgsConstructor
public class CotizacionOnDemandService implements CotizacionOnDemandPort {

    private static final int MONEDA_BASE = 1;
    private static final BigDecimal UNO = BigDecimal.ONE;
    private static final String UPSERT = """
            INSERT INTO moneda_cotizacion (
                moneda_cotizacion_id, fecha,
                moneda_id_origen, cotizacion_origen, copia_origen,
                moneda_id_destino, cotizacion_destino, copia_destino,
                coeficiente, created
            ) VALUES (
                :id, :fecha,
                :origen, :cotizacionOrigen, 0,
                :destino, :cotizacionDestino, 0,
                :coeficiente, NOW()
            )
            ON DUPLICATE KEY UPDATE
                cotizacion_origen = VALUES(cotizacion_origen),
                cotizacion_destino = VALUES(cotizacion_destino),
                coeficiente = VALUES(coeficiente),
                copia_origen = VALUES(copia_origen),
                copia_destino = VALUES(copia_destino)
            """;

    private final MonedaQueryPort monedaQuery;
    private final BcraCotizacionClient bcra;
    private final EntityManager entityManager;

    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public void asegurar(
            int negocioId,
            Integer monedaRegistroId,
            Integer monedaExpresionId,
            LocalDate desde,
            LocalDate hasta,
            List<LocalDate> fechasFaltantes) {
        if (fechasFaltantes == null || fechasFaltantes.isEmpty()
                || monedaRegistroId.equals(monedaExpresionId)) {
            return;
        }

        MonedaInfo origen = moneda(monedaRegistroId);
        MonedaInfo destino = moneda(monedaExpresionId);

        LocalDate min = fechasFaltantes.stream().min(LocalDate::compareTo).orElseThrow();
        LocalDate max = fechasFaltantes.stream().max(LocalDate::compareTo).orElseThrow();
        String clave = negocioId + "|" + monedaRegistroId + "|" + monedaExpresionId
                + "|" + min + "|" + max;

        synchronized (locks.computeIfAbsent(clave, ignored -> new Object())) {
            // Se pide una ventana previa para poder aplicar la última tasa
            // disponible cuando el primer día del rango es fin de semana o
            // feriado (mismo criterio que fillCotizacion).
            LocalDate consultaDesde = min.minusDays(10);
            NavigableMap<LocalDate, BigDecimal> cotizacionesOrigen = monedaRegistroId == MONEDA_BASE
                    ? new TreeMap<>()
                    : new TreeMap<>(bcra.cotizaciones(origen.simbolo(), consultaDesde, max));
            NavigableMap<LocalDate, BigDecimal> cotizacionesDestino = monedaExpresionId == MONEDA_BASE
                    ? new TreeMap<>()
                    : new TreeMap<>(bcra.cotizaciones(destino.simbolo(), consultaDesde, max));

            int insertadas = 0;
            // Se materializa una fila por día calendario con la última tasa
            // disponible (floor), igual que fillCotizacion. Así el reporte hace
            // un join por fecha exacta y no una subconsulta por movimiento.
            for (LocalDate fecha = min; !fecha.isAfter(max); fecha = fecha.plusDays(1)) {
                BigDecimal cotizacionOrigen = monedaRegistroId == MONEDA_BASE
                        ? UNO
                        : floor(cotizacionesOrigen, fecha);
                BigDecimal cotizacionDestino = monedaExpresionId == MONEDA_BASE
                        ? UNO
                        : floor(cotizacionesDestino, fecha);
                if (cotizacionOrigen == null || cotizacionDestino == null
                        || cotizacionDestino.signum() == 0) {
                    continue;
                }
                insertadas += upsert(
                        monedaRegistroId,
                        destino.monedaId(),
                        fecha,
                        cotizacionOrigen,
                        cotizacionDestino);
            }

            if (insertadas == 0) {
                throw new BalanceException(
                        "BALANCE_COTIZACION_FALTANTE",
                        "BCRA no devolvió tasas para " + origen.simbolo()
                                + "→" + destino.simbolo() + " entre " + min + " y " + max + ".");
            }
        }
    }

    private int upsert(
            Integer origenId,
            Integer destinoId,
            LocalDate fecha,
            BigDecimal cotizacionOrigen,
            BigDecimal cotizacionDestino) {
        BigDecimal coeficiente = cotizacionOrigen
                .divide(cotizacionDestino, 10, RoundingMode.HALF_UP);
        UUID uuid = UUID.randomUUID();
        ByteBuffer id = ByteBuffer.allocate(16)
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits());
        return entityManager.createNativeQuery(UPSERT)
                .setParameter("id", id.array())
                .setParameter("fecha", Timestamp.valueOf(fecha.atStartOfDay()))
                .setParameter("origen", origenId)
                .setParameter("cotizacionOrigen", cotizacionOrigen)
                .setParameter("destino", destinoId)
                .setParameter("cotizacionDestino", cotizacionDestino)
                .setParameter("coeficiente", coeficiente)
                .executeUpdate();
    }

    private static BigDecimal floor(NavigableMap<LocalDate, BigDecimal> tasas, LocalDate fecha) {
        Map.Entry<LocalDate, BigDecimal> entrada = tasas.floorEntry(fecha);
        return entrada == null ? null : entrada.getValue();
    }

    private MonedaInfo moneda(Integer monedaId) {
        return monedaQuery.findById(monedaId)
                .orElseThrow(() -> new BalanceException(
                        "BALANCE_MONEDA_INVALIDA",
                        "La moneda " + monedaId + " no existe en el catálogo del negocio."));
    }

}
