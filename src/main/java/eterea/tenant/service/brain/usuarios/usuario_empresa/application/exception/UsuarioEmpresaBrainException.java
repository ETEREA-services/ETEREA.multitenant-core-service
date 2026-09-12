package eterea.tenant.service.brain.usuarios.usuario_empresa.application.exception;

import java.util.UUID;

public class UsuarioEmpresaBrainException extends RuntimeException {

    public UsuarioEmpresaBrainException() {
        super("UsuarioEmpresa not found");
    }

    public UsuarioEmpresaBrainException(UUID usuarioEmpresaId) {
        super("Could not find UsuarioEmpresa with id: " + usuarioEmpresaId);
    }
}
