package eterea.tenant.service.brain.negocio.application.exception;

/** Conflicto al crear o modificar un negocio, por ejemplo un tenantId repetido. */
public class NegocioBrainConflictException extends RuntimeException {

    public NegocioBrainConflictException(String message) {
        super(message);
    }
}
