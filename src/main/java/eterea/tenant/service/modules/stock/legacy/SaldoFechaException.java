package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.articulo.domain.model.Articulo;
import java.time.OffsetDateTime;

public class SaldoFechaException extends RuntimeException {

    public SaldoFechaException(int centroStockId, String articuloId, OffsetDateTime fecha) {
        super("Cannot find SaldoFecha for CentroStock -> " + centroStockId + ", Articulo -> " + articuloId + ", Fecha -> " + fecha);
    }

}
