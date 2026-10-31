package eterea.tenant.service.modules.contable.legacy;

import java.math.BigDecimal;

public class CuentaException extends RuntimeException {

    public CuentaException(long numeroCuenta) {
        super("No existe la cuenta " + numeroCuenta);
    }

    public CuentaException(BigDecimal cuentaMaestro) {
        super("No existe la cuenta maestra " + cuentaMaestro);
    }

}
