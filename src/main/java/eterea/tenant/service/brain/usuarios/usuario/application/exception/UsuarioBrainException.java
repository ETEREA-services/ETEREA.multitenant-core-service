package eterea.tenant.service.brain.usuarios.usuario.application.exception;

import eterea.tenant.service.modules.platform.legacy.Usuario;
import java.util.UUID;

public class UsuarioBrainException extends RuntimeException {

    public UsuarioBrainException() {
        super("Usuario no encontrado");
    }

    public UsuarioBrainException(UUID usuarioId) {
        super("No se pudo encontrar el usuario con id: " + usuarioId);
    }

    public UsuarioBrainException(String message) {
        super(message);
    }
}
