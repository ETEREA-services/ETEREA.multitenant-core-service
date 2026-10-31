package eterea.tenant.service.modules.stock.legacy;


import eterea.tenant.service.modules.stock.articulo.domain.model.Articulo;
public class ArticuloCentroException extends RuntimeException {

    public ArticuloCentroException(int centroStockId, String articuloId) {
        super("Cannot find ArticuloCentro for CentroStock -> " + centroStockId + ", Articulo -> " + articuloId);
    }

}
