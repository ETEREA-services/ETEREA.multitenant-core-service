package eterea.tenant.service.brain.usuario.application.exception;

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
