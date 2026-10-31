package eterea.tenant.service.modules.stock.articulomovimiento.application.exception;


import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;
public class ArticuloMovimientoException extends RuntimeException {
    public ArticuloMovimientoException(Long articuloMovimientoId) {
        super("Cannot find ArticuloMovimiento " + articuloMovimientoId);
    }
}
