package eterea.tenant.service.brain.empresa.application.exception;

import eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.domain.model.Empresa;
import java.util.UUID;

public class EmpresaBrainException extends RuntimeException {

    public EmpresaBrainException() {
        super("Empresa not found");
    }

    public EmpresaBrainException(UUID empresaId) {
        super("Could not find Empresa with id: " + empresaId);
    }
}
