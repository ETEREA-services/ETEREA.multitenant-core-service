package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.application.exception;

public class UsuarioNegocioUnificadoException extends RuntimeException {

    public UsuarioNegocioUnificadoException() {
        super("Cannot find UsuarioNegocioUnificado");
    }

    public UsuarioNegocioUnificadoException(Integer usuarioNegocioId) {
        super("Cannot find UsuarioNegocioUnificado with id: " + usuarioNegocioId);
    }

    public UsuarioNegocioUnificadoException(Long usuarioId) {
        super("Cannot find UsuarioNegocioUnificado with usuarioId: " + usuarioId);
    }

}
