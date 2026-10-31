package eterea.tenant.service.modules.contable.legacy;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoRepositoryCustom;
import eterea.tenant.service.modules.contable.legacy.CuentaMovimiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class CuentaMovimientoRepositoryCustomImpl implements CuentaMovimientoRepositoryCustom {

    private final EntityManager entityManager;

    public CuentaMovimientoRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public BigDecimal calculateTotalByNumeroCuentaAndDebitaAndFechaBetween(
            Long numeroCuenta,
            Integer debita,
            OffsetDateTime desde,
            OffsetDateTime hasta) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<CuentaMovimiento> root = criteriaQuery.from(CuentaMovimiento.class);

        Expression<BigDecimal> sumImporte =
                criteriaBuilder.coalesce(criteriaBuilder.sum(root.get("importe")), BigDecimal.ZERO);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("numeroCuenta"), numeroCuenta));
        predicates.add(criteriaBuilder.equal(root.get("debita"), debita.byteValue()));
        predicates.add(criteriaBuilder.between(root.get("fecha"), desde, hasta));

        criteriaQuery.select(sumImporte).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public BigDecimal calculateTotalByNumeroCuentaAndDebitaAndInflacionAndFechaBetween(
            Long numeroCuenta,
            Integer debita,
            Integer inflacion,
            OffsetDateTime desde,
            OffsetDateTime hasta) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<CuentaMovimiento> root = criteriaQuery.from(CuentaMovimiento.class);

        Expression<BigDecimal> sumImporte =
                criteriaBuilder.coalesce(criteriaBuilder.sum(root.get("importe")), BigDecimal.ZERO);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("numeroCuenta"), numeroCuenta));
        predicates.add(criteriaBuilder.equal(root.get("debita"), debita.byteValue()));
        predicates.add(criteriaBuilder.equal(root.get("inflacion"), inflacion.byteValue()));
        predicates.add(criteriaBuilder.between(root.get("fecha"), desde, hasta));

        criteriaQuery.select(sumImporte).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteAllByFechaAndOrden(OffsetDateTime fechaContable, Integer ordenContable) {
        entityManager.createQuery(
                        "delete from CuentaMovimiento "
                                + "where fecha = :fechaContable "
                                + "and orden = :ordenContable")
                .setParameter("fechaContable", fechaContable)
                .setParameter("ordenContable", ordenContable)
                .executeUpdate();
    }

}
