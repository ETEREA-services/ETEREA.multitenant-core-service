package eterea.tenant.service.modules.contable.legacy;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoAperturaMonedaRepositoryCustom;
import jakarta.persistence.EntityManager;
import java.time.OffsetDateTime;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

public class CuentaMovimientoAperturaMonedaRepositoryCustomImpl
        implements CuentaMovimientoAperturaMonedaRepositoryCustom {

    private static final String INSERT_OR_UPDATE = """
            INSERT INTO cuenta_movimiento_apertura_moneda (
                cuenta_movimiento_apertura_moneda_id, fecha, orden, item, moneda_id, importe, created
            )
            SELECT
                UNHEX(REPLACE(UUID(), '-', '')),
                m.mca_fecha,
                m.mca_nrocomp,
                m.mca_item,
                c.moneda_id_destino,
                ROUND(m.mca_Importe * c.coeficiente, 5),
                NOW()
            FROM movconapertura m
            JOIN moneda_cotizacion c
            ON m.mca_fecha = c.fecha
            WHERE m.mca_fecha BETWEEN :fechaDesde AND :fechaHasta
            AND c.moneda_id_origen = :monedaIdOrigen
            AND c.moneda_id_destino = :monedaIdDestino
            ON DUPLICATE KEY UPDATE
                fecha = VALUES(fecha),
                orden = VALUES(orden),
                item = VALUES(item),
                moneda_id = VALUES(moneda_id),
                importe = VALUES(importe),
                created = VALUES(created)
            """;

    private static final String DELETE_ORPHANED = """
            DELETE cmm FROM cuenta_movimiento_apertura_moneda cmm
            WHERE cmm.fecha BETWEEN :fechaDesde AND :fechaHasta
            AND cmm.moneda_id = :monedaId
            AND NOT EXISTS (
                SELECT 1 FROM movconapertura m
                WHERE m.mca_fecha = cmm.fecha
                AND m.mca_nrocomp = cmm.orden
                AND m.mca_item = cmm.item
            )
            """;

    private final EntityManager entityManager;

    public CuentaMovimientoAperturaMonedaRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void insertOrUpdateMovimientosCotizados(
            Integer monedaIdOrigen,
            Integer monedaIdDestino,
            OffsetDateTime fechaDesde,
            OffsetDateTime fechaHasta) {
        entityManager.createNativeQuery(INSERT_OR_UPDATE)
                .setParameter("monedaIdOrigen", monedaIdOrigen)
                .setParameter("monedaIdDestino", monedaIdDestino)
                .setParameter("fechaDesde", fechaDesde)
                .setParameter("fechaHasta", fechaHasta)
                .executeUpdate();
    }

    @Override
    @Transactional
    public int deleteOrphanedRecords(
            Integer monedaId,
            OffsetDateTime fechaDesde,
            OffsetDateTime fechaHasta) {
        return entityManager.createNativeQuery(DELETE_ORPHANED)
                .setParameter("monedaId", monedaId)
                .setParameter("fechaDesde", fechaDesde)
                .setParameter("fechaHasta", fechaHasta)
                .executeUpdate();
    }

}
