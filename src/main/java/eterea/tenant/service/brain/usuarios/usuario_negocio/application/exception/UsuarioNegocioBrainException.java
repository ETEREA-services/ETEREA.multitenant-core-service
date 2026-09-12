package eterea.tenant.service.brain.usuarios.usuario_negocio.application.exception;

import java.util.UUID;

public class UsuarioNegocioBrainException extends RuntimeException {

    public UsuarioNegocioBrainException() {
        super("UsuarioNegocio not found");
    }

    public UsuarioNegocioBrainException(UUID usuarioNegocioId) {
        super("Could not find UsuarioNegocio with id: " + usuarioNegocioId);
    }

    public UsuarioNegocioBrainException(UUID usuarioId, Integer negocioId) {
        super("UsuarioNegocio already exists for usuario: " + usuarioId + " and negocio: " + negocioId);
    }
}
