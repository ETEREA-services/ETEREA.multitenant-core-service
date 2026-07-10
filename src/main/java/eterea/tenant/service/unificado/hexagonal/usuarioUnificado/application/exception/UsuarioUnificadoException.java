package eterea.tenant.service.unificado.hexagonal.usuarioUnificado.application.exception;

public class UsuarioUnificadoException extends RuntimeException {

    public UsuarioUnificadoException() {
        super("Cannot find UsuarioUnificado");
    }

    public UsuarioUnificadoException(Integer usuarioId) {
        super("Cannot find UsuarioUnificado with id: " + usuarioId);
    }

    public UsuarioUnificadoException(String login) {
        super("Cannot find UsuarioUnificado with login: " + login);
    }

}
