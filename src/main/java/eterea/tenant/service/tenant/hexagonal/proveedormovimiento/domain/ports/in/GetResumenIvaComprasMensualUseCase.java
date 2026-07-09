package eterea.tenant.service.tenant.hexagonal.proveedormovimiento.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.proveedormovimiento.domain.model.ResumenIvaComprasMensual;

public interface GetResumenIvaComprasMensualUseCase {

    ResumenIvaComprasMensual getResumenIvaComprasMensual(Integer anho, Integer mes);

}
