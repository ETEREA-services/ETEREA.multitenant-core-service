package eterea.tenant.service.modules.stock.legacy;


import eterea.tenant.service.modules.stock.articulo.domain.model.Articulo;
public class SaldoArticuloException extends RuntimeException {

    public SaldoArticuloException(int centroStockId, String articuloId) {
        super("Cannot find SaldoArticulo for CentroStock -> " + centroStockId + ", Articulo -> " + articuloId);
    }

}
