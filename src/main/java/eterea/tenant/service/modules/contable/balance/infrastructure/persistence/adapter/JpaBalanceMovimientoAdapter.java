package eterea.tenant.service.modules.contable.balance.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.SaldoCuenta;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.BalanceMovimientoPort;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de lectura de movimientos contables.
 *
 * <p>Usa SQL nativo vía {@link EntityManager} porque la conversión de moneda
 * necesita {@code ROUND(importe * coeficiente, 5)} y la resolución de la última
 * tasa por fecha; no se usa la anotación de consulta ni se importan entidades
 * de otros módulos.</p>
 */
@Repository
@RequiredArgsConstructor
public class JpaBalanceMovimientoAdapter implements BalanceMovimientoPort {

    private static final String SALDOS_MOVCON = """
            SELECT m.Cuenta AS cuenta,
                   SUM(CASE WHEN m.Debita <> 0 THEN %s ELSE 0 END) AS debe,
                   SUM(CASE WHEN m.Debita = 0 THEN %s ELSE 0 END) AS haber
            FROM movcon m
            %s
            WHERE m.MCo_Neg_ID = :negocioId
              AND m.fecha >= :desde
              AND m.fecha < :hastaExclusive
              %s
            GROUP BY m.Cuenta
            """;

    private static final String SALDOS_APERTURA = """
            SELECT m.MCA_Cuenta AS cuenta,
                   SUM(CASE WHEN m.MCA_Debita <> 0 THEN %s ELSE 0 END) AS debe,
                   SUM(CASE WHEN m.MCA_Debita = 0 THEN %s ELSE 0 END) AS haber
            FROM movconapertura m
            %s
            WHERE m.MCA_Neg_ID = :negocioId
              AND m.MCA_Fecha >= :desde
              AND m.MCA_Fecha < :hastaExclusive
            GROUP BY m.MCA_Cuenta
            """;

    private static final String JOIN_COTIZACION_MOVCON = """
            JOIN moneda_cotizacion c
              ON c.moneda_id_origen = :monedaOrigen
             AND c.moneda_id_destino = :monedaDestino
             AND c.fecha = m.fecha
             AND c.coeficiente IS NOT NULL
            """;

    private static final String JOIN_COTIZACION_APERTURA = """
            JOIN moneda_cotizacion c
              ON c.moneda_id_origen = :monedaOrigen
             AND c.moneda_id_destino = :monedaDestino
             AND c.fecha = DATE(m.MCA_Fecha)
             AND c.coeficiente IS NOT NULL
            """;

    private static final String FECHAS_SIN_COTIZACION_MOVCON = """
            SELECT DISTINCT m.fecha
            FROM movcon m
            WHERE m.MCo_Neg_ID = :negocioId
              AND m.fecha >= :desde
              AND m.fecha < :hastaExclusive
              %s
              AND NOT EXISTS (
                    SELECT 1
                    FROM moneda_cotizacion c
                    WHERE c.moneda_id_origen = :monedaOrigen
                      AND c.moneda_id_destino = :monedaDestino
                      AND c.fecha = m.fecha
                      AND c.coeficiente IS NOT NULL
              )
            """;

    private static final String FECHAS_SIN_COTIZACION_APERTURA = """
            SELECT DISTINCT m.MCA_Fecha
            FROM movconapertura m
            WHERE m.MCA_Neg_ID = :negocioId
              AND m.MCA_Fecha >= :desde
              AND m.MCA_Fecha < :hastaExclusive
              AND NOT EXISTS (
                    SELECT 1
                    FROM moneda_cotizacion c
                    WHERE c.moneda_id_origen = :monedaOrigen
                      AND c.moneda_id_destino = :monedaDestino
                      AND c.fecha = DATE(m.MCA_Fecha)
                      AND c.coeficiente IS NOT NULL
              )
            """;

    private final EntityManager entityManager;

    @Override
    public List<SaldoCuenta> findSaldos(int negocioId, BalanceFiltro filtro) {
        boolean convierte = !filtro.esMonedaBase();
        String importe = convierte ? "ROUND(m.Importe * c.coeficiente, 5)" : "m.Importe";
        String join = convierte ? JOIN_COTIZACION_MOVCON : "";
        return consultarSaldos(SALDOS_MOVCON, join, importe, negocioId, filtro, true);
    }

    @Override
    public List<SaldoCuenta> findSaldosApertura(int negocioId, BalanceFiltro filtro) {
        boolean convierte = !filtro.esMonedaBase();
        String importe = convierte ? "ROUND(m.MCA_Importe * c.coeficiente, 5)" : "m.MCA_Importe";
        String join = convierte ? JOIN_COTIZACION_APERTURA : "";
        return consultarSaldos(SALDOS_APERTURA, join, importe, negocioId, filtro, false);
    }

    @Override
    public List<LocalDate> findFechasSinCotizacion(int negocioId, BalanceFiltro filtro) {
        if (filtro.esMonedaBase()) {
            return List.of();
        }

        List<LocalDate> faltantes = new ArrayList<>();
        String filtroInflacion = filtro.incluyeInflacion() ? "" : "AND m.inflacion = 0";
        faltantes.addAll(consultarFechasSinCotizacion(
                FECHAS_SIN_COTIZACION_MOVCON.formatted(filtroInflacion), negocioId, filtro));
        if (filtro.incluyeApertura()) {
            faltantes.addAll(consultarFechasSinCotizacion(
                    FECHAS_SIN_COTIZACION_APERTURA, negocioId, filtro));
        }
        return faltantes.stream().distinct().sorted().toList();
    }

    @Override
    public String watermark(int negocioId, BalanceFiltro filtro) {
        String movimientos = consultarWatermarkRango("""
                SELECT COALESCE(MAX(m.updated), '1970-01-01 00:00:00')
                FROM movcon m
                WHERE m.MCo_Neg_ID = :negocioId
                  AND m.fecha >= :desde
                  AND m.fecha < :hastaExclusive
                """, negocioId, filtro);

        String apertura = filtro.incluyeApertura()
                ? consultarWatermarkRango("""
                        SELECT COALESCE(MAX(m.updated), '1970-01-01 00:00:00')
                        FROM movconapertura m
                        WHERE m.MCA_Neg_ID = :negocioId
                          AND m.MCA_Fecha >= :desde
                          AND m.MCA_Fecha < :hastaExclusive
                        """, negocioId, filtro)
                : "0";

        String cotizaciones = filtro.esMonedaBase() ? "0" : consultarWatermarkCotizacion("""
                SELECT COALESCE(MAX(c.updated), '1970-01-01 00:00:00')
                FROM moneda_cotizacion c
                WHERE c.moneda_id_origen = :monedaOrigen
                  AND c.moneda_id_destino = :monedaDestino
                  AND c.fecha <= :hasta
                """, filtro);

        return movimientos + "|" + apertura + "|" + cotizaciones;
    }

    private List<SaldoCuenta> consultarSaldos(
            String plantilla,
            String join,
            String importe,
            int negocioId,
            BalanceFiltro filtro,
            boolean aplicaInflacion) {
        boolean convierte = !filtro.esMonedaBase();
        String filtroInflacion = aplicaInflacion && !filtro.incluyeInflacion()
                ? "AND m.inflacion = 0"
                : "";

        String sql = plantilla.formatted(importe, importe, join, filtroInflacion);
        var query = entityManager.createNativeQuery(sql)
                .setParameter("negocioId", negocioId)
                .setParameter("desde", Date.valueOf(filtro.desde()))
                .setParameter("hastaExclusive", Date.valueOf(filtro.hasta().plusDays(1)));
        if (convierte) {
            query.setParameter("monedaOrigen", filtro.monedaRegistroId())
                    .setParameter("monedaDestino", filtro.monedaExpresionId());
        }

        @SuppressWarnings("unchecked")
        List<Object[]> filas = query.getResultList();
        List<SaldoCuenta> saldos = new ArrayList<>(filas.size());
        for (Object[] fila : filas) {
            saldos.add(new SaldoCuenta(
                    ((Number) fila[0]).longValue(),
                    numero(fila[1]),
                    numero(fila[2])));
        }
        return saldos;
    }

    private List<LocalDate> consultarFechasSinCotizacion(
            String sql, int negocioId, BalanceFiltro filtro) {
        @SuppressWarnings("unchecked")
        List<Object> filas = entityManager.createNativeQuery(sql)
                .setParameter("negocioId", negocioId)
                .setParameter("desde", Date.valueOf(filtro.desde()))
                .setParameter("hastaExclusive", Date.valueOf(filtro.hasta().plusDays(1)))
                .setParameter("monedaOrigen", filtro.monedaRegistroId())
                .setParameter("monedaDestino", filtro.monedaExpresionId())
                .getResultList();
        return filas.stream().map(JpaBalanceMovimientoAdapter::fecha).toList();
    }

    private String consultarWatermarkRango(String sql, int negocioId, BalanceFiltro filtro) {
        Object valor = entityManager.createNativeQuery(sql)
                .setParameter("negocioId", negocioId)
                .setParameter("desde", Date.valueOf(filtro.desde()))
                .setParameter("hastaExclusive", Date.valueOf(filtro.hasta().plusDays(1)))
                .getSingleResult();
        return valor == null ? "0" : valor.toString();
    }

    private String consultarWatermarkCotizacion(String sql, BalanceFiltro filtro) {
        Object valor = entityManager.createNativeQuery(sql)
                .setParameter("monedaOrigen", filtro.monedaRegistroId())
                .setParameter("monedaDestino", filtro.monedaExpresionId())
                .setParameter("hasta", Date.valueOf(filtro.hasta()))
                .getSingleResult();
        return valor == null ? "0" : valor.toString();
    }

    private static BigDecimal numero(Object valor) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }
        return valor instanceof BigDecimal decimal ? decimal : new BigDecimal(valor.toString());
    }

    private static LocalDate fecha(Object valor) {
        if (valor instanceof LocalDate localDate) {
            return localDate;
        }
        if (valor instanceof Date date) {
            return date.toLocalDate();
        }
        if (valor instanceof java.sql.Timestamp timestamp) {
            return timestamp.toLocalDateTime().toLocalDate();
        }
        if (valor instanceof java.time.LocalDateTime localDateTime) {
            return localDateTime.toLocalDate();
        }
        return LocalDate.parse(valor.toString());
    }

}
