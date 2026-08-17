package eterea.tenant.service.brain.usuario.domain.ports.in;

import java.util.UUID;

public interface ChangeUsuarioBrainPasswordUseCase {
    boolean change(UUID usuarioId, String currentPassword, String newPassword);
}
