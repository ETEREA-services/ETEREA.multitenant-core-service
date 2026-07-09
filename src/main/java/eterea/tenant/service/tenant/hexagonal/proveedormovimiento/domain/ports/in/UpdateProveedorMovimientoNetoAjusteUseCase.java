package eterea.tenant.service.tenant.hexagonal.proveedormovimiento.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.proveedormovimiento.domain.model.ProveedorMovimiento;
import eterea.tenant.service.tenant.hexagonal.proveedormovimiento.infrastructure.web.dto.ProveedorMovimientoNetoAjusteRequest;

public interface UpdateProveedorMovimientoNetoAjusteUseCase {
    ProveedorMovimiento updateProveedorMovimientoNetoAjuste(ProveedorMovimientoNetoAjusteRequest request);
}
