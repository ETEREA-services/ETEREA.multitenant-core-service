package eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.domain.ports.in;

import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.domain.model.ProveedorMovimiento;

import java.util.List;

public interface GetProveedorMovimientosByProveedorIdUseCase {
    List<ProveedorMovimiento> getProveedorMovimientosByProveedorId(Long proveedorId);
}
