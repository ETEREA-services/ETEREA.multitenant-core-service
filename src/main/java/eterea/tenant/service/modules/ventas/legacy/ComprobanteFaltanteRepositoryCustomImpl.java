package eterea.tenant.service.modules.ventas.legacy;

import eterea.tenant.service.modules.ventas.legacy.ComprobanteFaltanteRepositoryCustom;
import jakarta.persistence.EntityManager;
import java.time.OffsetDateTime;
import org.springframework.transaction.annotation.Transactional;

public class ComprobanteFaltanteRepositoryCustomImpl implements ComprobanteFaltanteRepositoryCustom {

    private final EntityManager entityManager;

    public ComprobanteFaltanteRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void deleteAllByFecha(OffsetDateTime fecha) {
        entityManager.createQuery("delete from ComprobanteFaltante where fecha = :fecha")
                .setParameter("fecha", fecha)
                .executeUpdate();
    }

}
