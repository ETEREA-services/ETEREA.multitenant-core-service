package eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;

public interface CreateUsuarioEmpresaBrainUseCase {

    UsuarioEmpresaBrain create(UsuarioEmpresaBrain usuarioEmpresa);
}
