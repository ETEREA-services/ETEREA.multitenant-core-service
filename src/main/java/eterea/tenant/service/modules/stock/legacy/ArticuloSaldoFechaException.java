package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.articulo.domain.model.Articulo;
import java.time.OffsetDateTime;

public class ArticuloSaldoFechaException extends RuntimeException {

    public ArticuloSaldoFechaException(int centroStockId, String articuloId, OffsetDateTime fecha) {
        super("Cannot find ArticuloSaldoFecha for CentroStock -> " + centroStockId + ", Articulo -> " + articuloId + ", Fecha -> " + fecha);
    }

}
