package eterea.tenant.service.brain.usuario.domain.ports.in;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import java.util.Optional;
import java.util.UUID;

public interface GetUsuarioBrainByIdUseCase {
    Optional<UsuarioBrain> getById(UUID usuarioId);
}
