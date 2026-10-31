package eterea.tenant.service.modules.stock.legacy.exception;


import eterea.tenant.service.modules.stock.legacy.ProductoArticulo;
public class ProductoArticuloException extends RuntimeException {

    public ProductoArticuloException(Integer productoId, String articuloId) {
        super("Cannot find ProductoArticulo " + productoId + "/" + articuloId);
    }
}
