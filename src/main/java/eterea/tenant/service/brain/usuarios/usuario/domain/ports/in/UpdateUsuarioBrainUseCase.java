package eterea.tenant.service.brain.usuarios.usuario.domain.ports.in;

import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import java.util.Optional;
import java.util.UUID;

public interface UpdateUsuarioBrainUseCase {
    Optional<UsuarioBrain> update(UUID usuarioId, UsuarioBrain usuario, String password);
}
