package eterea.tenant.service.brain.negocio.infrastructure.persistence.repository;

import eterea.tenant.service.brain.negocio.infrastructure.persistence.entity.NegocioBrainEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaNegocioBrainRepository extends JpaRepository<NegocioBrainEntity, Integer> {

    Optional<NegocioBrainEntity> findByTenantId(String tenantId);
}
