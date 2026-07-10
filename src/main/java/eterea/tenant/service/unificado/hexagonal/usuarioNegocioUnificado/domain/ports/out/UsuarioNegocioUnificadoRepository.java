package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.out;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import java.util.List;
import java.util.Optional;

public interface UsuarioNegocioUnificadoRepository {
    List<UsuarioNegocioUnificado> findAll();
    Optional<UsuarioNegocioUnificado> findByUsuarioNegocioId(Integer usuarioNegocioId);
    List<UsuarioNegocioUnificado> findByUsuarioId(Long usuarioId);
}
