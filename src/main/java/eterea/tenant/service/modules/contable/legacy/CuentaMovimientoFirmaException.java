package eterea.tenant.service.modules.contable.legacy;

import java.time.OffsetDateTime;

public class CuentaMovimientoFirmaException extends RuntimeException {

    public CuentaMovimientoFirmaException(OffsetDateTime fechaContable, int ordenContable) {
        super("No existe firma para fecha " + fechaContable + " y orden " + ordenContable);
    }

}
