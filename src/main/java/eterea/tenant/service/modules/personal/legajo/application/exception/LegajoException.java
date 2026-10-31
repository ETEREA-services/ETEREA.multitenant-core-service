package eterea.tenant.service.modules.personal.legajo.application.exception;


import eterea.tenant.service.modules.personal.legajo.domain.model.Legajo;
public class LegajoException extends RuntimeException {

    public LegajoException() {
        super("Cannot find Legajo");
    }

    public LegajoException(Integer legajoId) {
        super("Cannot find Legajo with id: " + legajoId);
    }

}
