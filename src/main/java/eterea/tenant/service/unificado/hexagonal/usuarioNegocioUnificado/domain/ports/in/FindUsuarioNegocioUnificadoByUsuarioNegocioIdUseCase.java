package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.ports.in;

import eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model.UsuarioNegocioUnificado;

public interface FindUsuarioNegocioUnificadoByUsuarioNegocioIdUseCase {
    UsuarioNegocioUnificado findByUsuarioNegocioId(Integer usuarioNegocioId);
}
