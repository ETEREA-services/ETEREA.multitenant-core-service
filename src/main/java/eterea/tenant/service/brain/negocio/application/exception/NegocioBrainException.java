package eterea.tenant.service.brain.negocio.application.exception;

public class NegocioBrainException extends RuntimeException {

    public NegocioBrainException() {
        super("Negocio not found");
    }

    public NegocioBrainException(Integer negocioId) {
        super("Could not find Negocio with id: " + negocioId);
    }

    public NegocioBrainException(String tenantId) {
        super("Could not find Negocio with tenantId: " + tenantId);
    }
}
