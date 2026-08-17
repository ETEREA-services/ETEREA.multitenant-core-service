package eterea.tenant.service.brain.usuario.domain.ports.in;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;

public interface CreateUsuarioBrainUseCase {
    UsuarioBrain create(UsuarioBrain usuario, String password);
}
