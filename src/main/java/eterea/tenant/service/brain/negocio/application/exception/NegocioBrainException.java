package eterea.tenant.service.brain.negocio.application.exception;


import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
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
