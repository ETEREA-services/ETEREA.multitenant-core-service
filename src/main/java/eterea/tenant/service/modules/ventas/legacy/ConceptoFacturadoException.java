package eterea.tenant.service.modules.ventas.legacy;


import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;
public class ConceptoFacturadoException extends RuntimeException {

    public ConceptoFacturadoException(long articuloMovimientoId) {
        super("Cannot find ConceptoFacturado by ArticuloMovimiento= " + articuloMovimientoId);
    }

}
