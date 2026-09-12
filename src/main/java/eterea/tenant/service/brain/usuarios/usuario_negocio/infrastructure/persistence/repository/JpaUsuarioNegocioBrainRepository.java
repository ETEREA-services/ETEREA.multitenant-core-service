package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.repository;

import eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.entity.UsuarioNegocioBrainEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsuarioNegocioBrainRepository extends JpaRepository<UsuarioNegocioBrainEntity, UUID> {

    List<UsuarioNegocioBrainEntity> findByUsuarioId(UUID usuarioId);

    Optional<UsuarioNegocioBrainEntity> findByUsuarioIdAndNegocioId(UUID usuarioId, Integer negocioId);
}
