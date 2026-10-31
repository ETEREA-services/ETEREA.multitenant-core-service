package eterea.tenant.service.modules.stock.legacy;

import java.time.OffsetDateTime;

public class InventarioException extends RuntimeException {

    public InventarioException(OffsetDateTime fecha, int inventarioTurnoId, int centroStockId, String articuloId) {
        super("Inventario not found fecha: " + fecha + ", inventarioTurnoId: " + inventarioTurnoId + ", centroStockId: " + centroStockId + ", articuloId: " + articuloId);
    }

}
