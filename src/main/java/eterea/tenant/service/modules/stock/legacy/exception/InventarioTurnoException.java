package eterea.tenant.service.modules.stock.legacy.exception;


import eterea.tenant.service.modules.stock.legacy.InventarioTurno;
public class InventarioTurnoException extends RuntimeException {

    public InventarioTurnoException(Integer inventarioTurnoId) {
        super("Cannot find InventarioTurno with id " + inventarioTurnoId);
    }

}
