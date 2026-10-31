package eterea.tenant.service.modules.platform.legacy.repository.impl;

import eterea.tenant.service.modules.platform.legacy.repository.GrupoRepositoryCustom;
import jakarta.persistence.EntityManager;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Grupos asociados a los vouchers de una fecha de servicio.
 *
 * <p>La consulta original usaba {@code FETCH JOIN} sobre tablas, que no es SQL
 * válido en MySQL; se reescribe como un join estándar con la misma semántica.</p>
 */
public class GrupoRepositoryCustomImpl implements GrupoRepositoryCustom {

    private final EntityManager entityManager;

    public GrupoRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> findAllByFecha(OffsetDateTime fecha) {
        List<?> result = entityManager.createNativeQuery(
                        "SELECT DISTINCT gp.GrP_Gru_ID "
                                + "FROM voucher vou "
                                + "JOIN voucherproducto vp ON vou.Vou_ID = vp.Vou_ID "
                                + "JOIN grupoproducto gp ON vp.VPr_Prd_ID = gp.GrP_Prd_ID "
                                + "WHERE vou.Vou_FechaIn = :fecha")
                .setParameter("fecha", fecha)
                .getResultList();
        return result.stream()
                .map(value -> ((Number) value).intValue())
                .toList();
    }

}
