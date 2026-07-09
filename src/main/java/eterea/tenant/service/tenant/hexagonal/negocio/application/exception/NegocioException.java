package eterea.tenant.service.tenant.hexagonal.negocio.application.exception;

public class NegocioException extends RuntimeException {

    public NegocioException() {
        super("Cannot find Negocio");
    }

    public NegocioException(Integer negocioId) {
        super("Cannot find Negocio with id: " + negocioId);
    }

}
