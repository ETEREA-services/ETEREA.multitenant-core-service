package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;
import java.util.List;

public interface FindUsuarioNegocioUnificadoByUsuarioIdUseCase {
    List<UsuarioNegocioUnificado> findByUsuarioId(Long usuarioId);
}
