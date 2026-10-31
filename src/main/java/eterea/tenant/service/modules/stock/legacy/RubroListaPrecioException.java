package eterea.tenant.service.modules.stock.legacy;

public class RubroListaPrecioException extends RuntimeException {

    public RubroListaPrecioException(long rubroId) {
        super("Cannot find Rubro " + rubroId);
    }

}
