package eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.persistence.repository;

import eterea.tenant.service.brain.usuarios.usuario_empresa.infrastructure.persistence.entity.UsuarioEmpresaBrainEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsuarioEmpresaBrainRepository extends JpaRepository<UsuarioEmpresaBrainEntity, UUID> {

    List<UsuarioEmpresaBrainEntity> findByUsuarioId(UUID usuarioId);
}
