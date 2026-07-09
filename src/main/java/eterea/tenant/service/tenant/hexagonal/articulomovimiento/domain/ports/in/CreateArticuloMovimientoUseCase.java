package eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.model.ArticuloMovimiento;

public interface CreateArticuloMovimientoUseCase {
    ArticuloMovimiento add(ArticuloMovimiento articuloMovimiento);
}
