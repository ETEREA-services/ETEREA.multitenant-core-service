package eterea.tenant.service.modules.stock.legacy;

public class ArticuloListaPrecioException extends RuntimeException {

    public ArticuloListaPrecioException(String articuloId) {
        super("ArticuloListaPrecio not found for articuloId: " + articuloId);
    }

}
