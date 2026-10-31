package eterea.tenant.service.modules.ventas.legacy.repository.impl;

import eterea.tenant.service.modules.ventas.legacy.repository.ClienteMovimientoRepositoryCustom;
import eterea.tenant.service.modules.ventas.legacy.model.ClienteMovimiento;
import eterea.tenant.service.modules.ventas.legacy.repository.ClienteMovimientoRepositoryCustom;
import jakarta.persistence.EntityManager;
import java.time.OffsetDateTime;
import org.springframework.transaction.annotation.Transactional;

public class ClienteMovimientoRepositoryCustomImpl implements ClienteMovimientoRepositoryCustom {

    private final EntityManager entityManager;

    public ClienteMovimientoRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void deleteAllByFechaComprobanteAndComprobanteIdAndPuntoVentaAndNumeroComprobante(
            OffsetDateTime fechaComprobante,
            Integer comprobanteId,
            Integer puntoVenta,
            Long numeroComprobante) {
        entityManager.createQuery(
                        "delete from ClienteMovimiento "
                                + "where fechaComprobante = :fechaComprobante "
                                + "and comprobanteId = :comprobanteId "
                                + "and puntoVenta = :puntoVenta "
                                + "and numeroComprobante = :numeroComprobante")
                .setParameter("fechaComprobante", fechaComprobante)
                .setParameter("comprobanteId", comprobanteId)
                .setParameter("puntoVenta", puntoVenta)
                .setParameter("numeroComprobante", numeroComprobante)
                .executeUpdate();
    }

}
