package eterea.tenant.service.modules.platform.negocio.application.exception;


import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
public class NegocioException extends RuntimeException {

    public NegocioException() {
        super("Cannot find Negocio");
    }

    public NegocioException(Integer negocioId) {
        super("Cannot find Negocio with id: " + negocioId);
    }

}
