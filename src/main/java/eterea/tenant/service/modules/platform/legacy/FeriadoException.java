package eterea.tenant.service.modules.platform.legacy;

import java.time.OffsetDateTime;

public class FeriadoException extends RuntimeException {

    public FeriadoException(OffsetDateTime fecha) {
        super("Cannot find Feriado " + fecha);
    }

}
