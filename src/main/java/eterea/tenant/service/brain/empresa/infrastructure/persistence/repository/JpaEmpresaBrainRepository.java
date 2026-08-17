package eterea.tenant.service.brain.empresa.infrastructure.persistence.repository;

import eterea.tenant.service.brain.empresa.infrastructure.persistence.entity.EmpresaBrainEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEmpresaBrainRepository extends JpaRepository<EmpresaBrainEntity, UUID> {
}
