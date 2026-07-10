package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.repository;

import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.persistence.entity.UsuarioUnificadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUsuarioUnificadoRepository extends JpaRepository<UsuarioUnificadoEntity, Integer> {

    Optional<UsuarioUnificadoEntity> findByUsuarioId(Integer usuarioId);

    Optional<UsuarioUnificadoEntity> findByLogin(String login);

}
