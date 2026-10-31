package eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.domain.ports.in;

import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.domain.model.ProveedorMovimiento;
import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.web.dto.ProveedorMovimientoNetoAjusteRequest;

public interface UpdateProveedorMovimientoNetoAjusteUseCase {
    ProveedorMovimiento updateProveedorMovimientoNetoAjuste(ProveedorMovimientoNetoAjusteRequest request);
}
