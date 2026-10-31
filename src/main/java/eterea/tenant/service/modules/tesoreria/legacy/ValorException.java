package eterea.tenant.service.modules.tesoreria.legacy;

public class ValorException extends RuntimeException {

    public ValorException(int valorId) {
        super("Cannot find Valor " + valorId);
    }

}
