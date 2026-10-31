package eterea.tenant.service.modules.tesoreria.legacy;

public class ValorMovimientoException extends RuntimeException {

    public ValorMovimientoException(long valorMovimientoId) {
        super("Cannot find ValorMovimiento " + valorMovimientoId);
    }

}
