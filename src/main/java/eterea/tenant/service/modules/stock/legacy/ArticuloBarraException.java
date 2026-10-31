package eterea.tenant.service.modules.stock.legacy;

public class ArticuloBarraException extends RuntimeException {

    public ArticuloBarraException(String codigobarras) {
        super("Cannot found ArticuloBarra with code " + codigobarras);
    }

}
