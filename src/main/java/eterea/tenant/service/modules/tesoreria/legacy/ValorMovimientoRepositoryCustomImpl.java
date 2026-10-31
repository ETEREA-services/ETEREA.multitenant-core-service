package eterea.tenant.service.modules.tesoreria.legacy;

import eterea.tenant.service.modules.tesoreria.legacy.ValorMovimiento;
import eterea.tenant.service.modules.tesoreria.legacy.ValorMovimientoRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class ValorMovimientoRepositoryCustomImpl implements ValorMovimientoRepositoryCustom {

    private final EntityManager entityManager;

    public ValorMovimientoRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ValorMovimiento> findAllByFechaContableBetween(
            OffsetDateTime desde,
            OffsetDateTime hasta,
            boolean cierreCajaOnly,
            boolean ingresosOnly) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ValorMovimiento> criteriaQuery = criteriaBuilder.createQuery(ValorMovimiento.class);
        Root<ValorMovimiento> root = criteriaQuery.from(ValorMovimiento.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaContable"), desde));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaContable"), hasta));

        if (cierreCajaOnly) {
            predicates.add(criteriaBuilder.isNotNull(root.get("cierreCajaId")));
            predicates.add(criteriaBuilder.notEqual(root.get("cierreCajaId"), 0L));
        }
        if (ingresosOnly) {
            predicates.add(criteriaBuilder.greaterThan(root.get("importe"), BigDecimal.ZERO));
        }

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    @Transactional
    public void deleteAllByFechaContableAndOrdenContable(
            OffsetDateTime fechaContable,
            Integer ordenContable) {
        entityManager.createQuery(
                        "delete from ValorMovimiento "
                                + "where fechaContable = :fechaContable "
                                + "and ordenContable = :ordenContable")
                .setParameter("fechaContable", fechaContable)
                .setParameter("ordenContable", ordenContable)
                .executeUpdate();
    }

}
