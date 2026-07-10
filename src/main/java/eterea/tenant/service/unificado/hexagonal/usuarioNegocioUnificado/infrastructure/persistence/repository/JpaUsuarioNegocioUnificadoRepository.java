package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.persistence.repository;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.persistence.entity.UsuarioNegocioUnificadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUsuarioNegocioUnificadoRepository extends JpaRepository<UsuarioNegocioUnificadoEntity, Integer> {

    Optional<UsuarioNegocioUnificadoEntity> findByUsuarioNegocioId(Integer usuarioNegocioId);

    List<UsuarioNegocioUnificadoEntity> findByUsuarioId(Long usuarioId);

}
