package eterea.tenant.service.unificado.hexagonal.negocioUnificado.application.exception;

public class NegocioUnificadoException extends RuntimeException {

    public NegocioUnificadoException() {
        super("Cannot find NegocioUnificado");
    }

    public NegocioUnificadoException(Integer negocioId) {
        super("Cannot find NegocioUnificado with id: " + negocioId);
    }

}
