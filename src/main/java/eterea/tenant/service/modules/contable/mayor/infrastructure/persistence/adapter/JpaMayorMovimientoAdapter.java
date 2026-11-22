package eterea.tenant.service.modules.contable.mayor.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDetalle;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFila;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorMovimientoPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Adaptador de lectura de {@code movcon} para el Mayor.
 *
 * <p>Usa SQL nativo vía {@link EntityManager} porque necesita el join opcional
 * con {@code moneda_cotizacion} y el redondeo a 5 decimales, igual que el
 * adaptador del balance. No usa la anotación de consulta.</p>
 */
@Repository
@RequiredArgsConstructor
public class JpaMayorMovimientoAdapter implements MayorMovimientoPort {

    private static final String DETALLE = """
            SELECT m.fecha, m.CgoTComp, t.Descripcion, m.concepto, m.nrocomp,
                   CASE WHEN m.Debita <> 0 THEN %s ELSE 0 END AS debe,
                   CASE WHEN m.Debita = 0 THEN %s ELSE 0 END AS haber,
                   m.CgoProv, p.razon
            FROM movcon m
            %s
            INNER JOIN tiposcomprob t ON t.codigo = m.CgoTComp
            LEFT JOIN proveedores p ON p.codigo = m.CgoProv
            WHERE m.MCo_Neg_ID = :negocioId
              AND m.Cuenta = :cuenta
              AND m.fecha >= :desde
              AND m.fecha < :hastaExclusive
            ORDER BY m.fecha, m.nrocomp, m.Id
            """;

    private static final String SALDO_ANTERIOR = """
            SELECT COALESCE(SUM(CASE WHEN m.Debita <> 0 THEN %s ELSE 0 END), 0)
                 - COALESCE(SUM(CASE WHEN m.Debita = 0 THEN %s ELSE 0 END), 0)
            FROM movcon m
            %s
            WHERE m.MCo_Neg_ID = :negocioId
              AND m.Cuenta = :cuenta
              AND m.fecha < :desde
            """;

    private static final String DETALLE_DISCRIMINADO = """
            SELECT m.fecha, m.Cuenta, pc.nombre, t.Descripcion, m.concepto, m.nrocomp,
                   CASE WHEN m.Debita <> 0 THEN %s ELSE 0 END AS debe,
                   CASE WHEN m.Debita = 0 THEN %s ELSE 0 END AS haber,
                   p.razon
            FROM movcon m
            %s
            INNER JOIN tiposcomprob t ON t.codigo = m.CgoTComp
            LEFT JOIN plancta pc ON pc.cuenta = m.Cuenta
            LEFT JOIN proveedores p ON p.codigo = m.CgoProv
            WHERE m.MCo_Neg_ID = ?
              AND m.Cuenta IN (%s)
              AND m.fecha >= ?
              AND m.fecha < ?
            ORDER BY m.fecha, m.Cuenta, m.Id
            """;

    private static final String FECHAS_SIN_COTIZACION = """
            SELECT DISTINCT m.fecha
            FROM movcon m
            WHERE m.MCo_Neg_ID = ?
              AND m.Cuenta IN (%s)
              AND m.fecha >= ?
              AND m.fecha < ?
              AND NOT EXISTS (
                    SELECT 1
                    FROM moneda_cotizacion c
                    WHERE c.fecha = m.fecha
                      AND c.moneda_id_origen = ?
                      AND c.moneda_id_destino = ?
                      AND c.coeficiente IS NOT NULL
              )
            """;

    private final EntityManager entityManager;

    @Override
    public List<MayorDetalle> findDetalle(int negocioId, MayorFiltro filtro) {
        boolean convierte = !filtro.esMonedaBase();
        String importe = convierte ? "ROUND(m.Importe * c.coeficiente, 5)" : "m.Importe";
        String join = convierte ? joinCotizacion("m.fecha") : "";
        Query query = entityManager.createNativeQuery(
                        DETALLE.formatted(importe, importe, join))
                .setParameter("negocioId", negocioId)
                .setParameter("cuenta", filtro.cuenta())
                .setParameter("desde", Date.valueOf(filtro.desde()))
                .setParameter("hastaExclusive",
                        Date.valueOf(filtro.hasta().plusDays(1)));
        if (convierte) {
            query.setParameter("monedaOrigen", filtro.monedaRegistroId())
                    .setParameter("monedaDestino", filtro.monedaExpresionId());
        }

        @SuppressWarnings("unchecked")
        List<Object[]> filas = query.getResultList();
        List<MayorDetalle> detalles = new ArrayList<>(filas.size());
        for (Object[] fila : filas) {
            detalles.add(new MayorDetalle(
                    fecha(fila[0]),
                    entero(fila[1]),
                    texto(fila[2]),
                    texto(fila[3]),
                    entero(fila[4]),
                    numero(fila[5]),
                    numero(fila[6]),
                    largo(fila[7]),
                    texto(fila[8])));
        }
        return detalles;
    }

    @Override
    public BigDecimal findSaldoAnterior(int negocioId, MayorFiltro filtro) {
        boolean convierte = !filtro.esMonedaBase();
        String importe = convierte ? "ROUND(m.Importe * c.coeficiente, 5)" : "m.Importe";
        String join = convierte ? joinCotizacion(":fechaApertura") : "";
        Query query = entityManager.createNativeQuery(
                        SALDO_ANTERIOR.formatted(importe, importe, join))
                .setParameter("negocioId", negocioId)
                .setParameter("cuenta", filtro.cuenta())
                .setParameter("desde", Date.valueOf(filtro.desde()));
        if (convierte) {
            query.setParameter("monedaOrigen", filtro.monedaRegistroId())
                    .setParameter("monedaDestino", filtro.monedaExpresionId())
                    .setParameter("fechaApertura",
                            Date.valueOf(filtro.fechaApertura()));
        }
        Object valor = query.getSingleResult();
        return valor == null ? BigDecimal.ZERO : new BigDecimal(valor.toString());
    }

    @Override
    public List<MayorDiscriminadoFila> findDetalleDiscriminado(
            int negocioId, MayorDiscriminadoFiltro filtro) {
        boolean convierte = !filtro.esMonedaBase();
        String importe = convierte ? "ROUND(m.Importe * c.coeficiente, 5)" : "m.Importe";
        String join = convierte ? joinCotizacionPosicional("m.fecha") : "";
        String in = String.join(",", Collections.nCopies(
                filtro.cuentas().size(), "?"));
        String sql = DETALLE_DISCRIMINADO.formatted(importe, importe, join, in);
        Query query = entityManager.createNativeQuery(sql);

        int indice = 1;
        if (convierte) {
            query.setParameter(indice++, filtro.monedaRegistroId());
            query.setParameter(indice++, filtro.monedaExpresionId());
        }
        query.setParameter(indice++, negocioId);
        for (Long cuenta : filtro.cuentas()) {
            query.setParameter(indice++, cuenta);
        }
        query.setParameter(indice++, Date.valueOf(filtro.desde()));
        query.setParameter(indice, Date.valueOf(filtro.hasta().plusDays(1)));

        @SuppressWarnings("unchecked")
        List<Object[]> filas = query.getResultList();
        List<MayorDiscriminadoFila> resultado = new ArrayList<>(filas.size());
        for (Object[] fila : filas) {
            resultado.add(new MayorDiscriminadoFila(
                    fecha(fila[0]),
                    largo(fila[1]),
                    texto(fila[2]),
                    texto(fila[3]),
                    texto(fila[4]),
                    entero(fila[5]),
                    numero(fila[6]),
                    numero(fila[7]),
                    texto(fila[8])));
        }
        return resultado;
    }

    @Override
    public List<LocalDate> findFechasSinCotizacion(int negocioId, MayorFiltro filtro) {
        if (filtro.esMonedaBase()) {
            return List.of();
        }
        Set<LocalDate> faltantes = new LinkedHashSet<>(fechasSinCotizacion(
                negocioId,
                List.of(filtro.cuenta()),
                filtro.desde(),
                filtro.hasta(),
                filtro.monedaRegistroId(),
                filtro.monedaExpresionId()));
        if (!filtro.sinSaldoAnterior() && !existeCotizacion(
                filtro.fechaApertura(),
                filtro.monedaRegistroId(),
                filtro.monedaExpresionId())) {
            faltantes.add(filtro.fechaApertura());
        }
        return faltantes.stream().sorted().toList();
    }

    @Override
    public List<LocalDate> findFechasSinCotizacionDiscriminado(
            int negocioId, MayorDiscriminadoFiltro filtro) {
        if (filtro.esMonedaBase()) {
            return List.of();
        }
        return fechasSinCotizacion(
                negocioId,
                filtro.cuentas(),
                filtro.desde(),
                filtro.hasta(),
                filtro.monedaRegistroId(),
                filtro.monedaExpresionId());
    }

    private List<LocalDate> fechasSinCotizacion(
            int negocioId,
            List<Long> cuentas,
            LocalDate desde,
            LocalDate hasta,
            Integer monedaOrigen,
            Integer monedaDestino) {
        String in = String.join(",", Collections.nCopies(cuentas.size(), "?"));
        String sql = FECHAS_SIN_COTIZACION.formatted(in);
        Query query = entityManager.createNativeQuery(sql);
        int indice = 1;
        query.setParameter(indice++, negocioId);
        for (Long cuenta : cuentas) {
            query.setParameter(indice++, cuenta);
        }
        query.setParameter(indice++, Date.valueOf(desde));
        query.setParameter(indice++, Date.valueOf(hasta.plusDays(1)));
        query.setParameter(indice++, monedaOrigen);
        query.setParameter(indice, monedaDestino);

        @SuppressWarnings("unchecked")
        List<Object> filas = query.getResultList();
        return filas.stream().map(JpaMayorMovimientoAdapter::fecha).toList();
    }

    private boolean existeCotizacion(
            LocalDate fecha, Integer monedaOrigen, Integer monedaDestino) {
        Query query = entityManager.createNativeQuery("""
                SELECT 1
                FROM moneda_cotizacion c
                WHERE c.fecha = :fecha
                  AND c.moneda_id_origen = :origen
                  AND c.moneda_id_destino = :destino
                  AND c.coeficiente IS NOT NULL
                LIMIT 1
                """)
                .setParameter("fecha", Date.valueOf(fecha))
                .setParameter("origen", monedaOrigen)
                .setParameter("destino", monedaDestino);
        return !query.getResultList().isEmpty();
    }

    private static String joinCotizacion(String fecha) {
        return """
                JOIN moneda_cotizacion c
                  ON c.fecha = %s
                 AND c.moneda_id_origen = :monedaOrigen
                 AND c.moneda_id_destino = :monedaDestino
                 AND c.coeficiente IS NOT NULL
                """.formatted(fecha);
    }

    private static String joinCotizacionPosicional(String fecha) {
        return """
                JOIN moneda_cotizacion c
                  ON c.fecha = %s
                 AND c.moneda_id_origen = ?
                 AND c.moneda_id_destino = ?
                 AND c.coeficiente IS NOT NULL
                """.formatted(fecha);
    }

    private static LocalDate fecha(Object valor) {
        if (valor instanceof LocalDate localDate) {
            return localDate;
        }
        if (valor instanceof Date date) {
            return date.toLocalDate();
        }
        if (valor instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime().toLocalDate();
        }
        if (valor instanceof LocalDateTime localDateTime) {
            return localDateTime.toLocalDate();
        }
        return LocalDate.parse(valor.toString().substring(0, 10));
    }

    private static BigDecimal numero(Object valor) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }
        return valor instanceof BigDecimal decimal
                ? decimal : new BigDecimal(valor.toString());
    }

    private static Integer entero(Object valor) {
        return valor == null ? 0 : ((Number) valor).intValue();
    }

    private static Long largo(Object valor) {
        return valor == null ? 0L : ((Number) valor).longValue();
    }

    private static String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }

}
