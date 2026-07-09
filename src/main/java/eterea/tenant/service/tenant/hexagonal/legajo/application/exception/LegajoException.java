package eterea.tenant.service.tenant.hexagonal.legajo.application.exception;

public class LegajoException extends RuntimeException {

    public LegajoException() {
        super("Cannot find Legajo");
    }

    public LegajoException(Integer legajoId) {
        super("Cannot find Legajo with id: " + legajoId);
    }

}
