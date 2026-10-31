package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.repository;

import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.entity.CoeficienteInflacionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class JpaCoeficienteInflacionRepositoryCustomImpl
        implements JpaCoeficienteInflacionRepositoryCustom {

    private final EntityManager entityManager;

    public JpaCoeficienteInflacionRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Integer> findAniosDistintos() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<CoeficienteInflacionEntity> root =
                criteriaQuery.from(CoeficienteInflacionEntity.class);
        Expression<Integer> anho = root.get("anho").as(Integer.class);

        criteriaQuery.select(anho).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.desc(anho));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
