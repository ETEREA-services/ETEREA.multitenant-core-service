package eterea.tenant.service.brain.usuarios.usuario.domain.ports.in;

import java.time.OffsetDateTime;
import java.util.UUID;

/** Genera una contraseña temporal y obliga a cambiarla en el próximo ingreso. */
public interface ResetUsuarioBrainPasswordUseCase {

    TemporaryPassword reset(UUID usuarioId);

    record TemporaryPassword(String temporaryPassword, OffsetDateTime expiresAt) {
    }
}
