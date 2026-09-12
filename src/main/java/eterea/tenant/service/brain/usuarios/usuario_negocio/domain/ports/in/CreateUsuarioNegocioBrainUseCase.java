package eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;

public interface CreateUsuarioNegocioBrainUseCase {

    UsuarioNegocioBrain create(UsuarioNegocioBrain usuarioNegocio);
}
