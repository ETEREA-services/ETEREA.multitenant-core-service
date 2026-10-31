package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.ArticuloSaldoFecha;
import eterea.tenant.service.modules.stock.legacy.ArticuloSaldoFechaRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ArticuloSaldoFechaRepositoryCustomImpl implements ArticuloSaldoFechaRepositoryCustom {

    private final EntityManager entityManager;

    public ArticuloSaldoFechaRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public BigDecimal calculateSaldo(Integer centroStockId, String articuloId, OffsetDateTime tope) {
        if (centroStockId == null || articuloId == null || tope == null) {
            return BigDecimal.ZERO;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<ArticuloSaldoFecha> root = criteriaQuery.from(ArticuloSaldoFecha.class);

        Expression<BigDecimal> sumSaldo =
                criteriaBuilder.coalesce(criteriaBuilder.sum(root.get("saldo")), BigDecimal.ZERO);

        criteriaQuery.select(sumSaldo).where(
                criteriaBuilder.equal(root.get("centroStockId"), centroStockId),
                criteriaBuilder.equal(root.get("articuloId"), articuloId),
                criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), tope));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
