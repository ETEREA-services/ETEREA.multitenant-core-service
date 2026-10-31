package eterea.tenant.service.modules.stock.articulomovimiento.domain.ports.in;

import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;

import java.util.List;

public interface GetArticuloMovimientosByClienteMovimientoIdUseCase {
    List<ArticuloMovimiento> findAllByClienteMovimientoId(Long clienteMovimientoId);
}
