package eterea.tenant.service.brain.usuario.infrastructure.persistence.repository;

import eterea.tenant.service.brain.usuario.infrastructure.persistence.entity.UsuarioBrainEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsuarioBrainRepository extends JpaRepository<UsuarioBrainEntity, UUID> {
    Optional<UsuarioBrainEntity> findByLogin(String login);
}
