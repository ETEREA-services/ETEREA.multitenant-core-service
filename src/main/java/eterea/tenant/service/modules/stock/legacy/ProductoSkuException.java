package eterea.tenant.service.modules.stock.legacy;

public class ProductoSkuException extends RuntimeException {

    public ProductoSkuException(String sku) {
        super("Cannot find ProductoSku " + sku);
    }

}
