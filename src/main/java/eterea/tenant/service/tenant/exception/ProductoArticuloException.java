package eterea.tenant.service.tenant.exception;

public class ProductoArticuloException extends RuntimeException {

    public ProductoArticuloException(Integer productoId, String articuloId) {
        super("Cannot find ProductoArticulo " + productoId + "/" + articuloId);
    }
}
