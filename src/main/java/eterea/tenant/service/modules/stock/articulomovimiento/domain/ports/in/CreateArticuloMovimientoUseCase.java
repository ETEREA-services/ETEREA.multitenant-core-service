package eterea.tenant.service.modules.stock.articulomovimiento.domain.ports.in;

import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;

public interface CreateArticuloMovimientoUseCase {
    ArticuloMovimiento add(ArticuloMovimiento articuloMovimiento);
}
