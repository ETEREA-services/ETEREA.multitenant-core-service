package eterea.tenant.service.brain.empresa.application.exception;

import java.util.UUID;

public class EmpresaBrainException extends RuntimeException {

    public EmpresaBrainException() {
        super("Empresa not found");
    }

    public EmpresaBrainException(UUID empresaId) {
        super("Could not find Empresa with id: " + empresaId);
    }
}
