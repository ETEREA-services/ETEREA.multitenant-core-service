package eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.model.ArticuloMovimiento;

import java.util.List;

public interface SaveAllArticuloMovimientosUseCase {
    List<ArticuloMovimiento> saveAll(List<ArticuloMovimiento> articuloMovimientos);
}
