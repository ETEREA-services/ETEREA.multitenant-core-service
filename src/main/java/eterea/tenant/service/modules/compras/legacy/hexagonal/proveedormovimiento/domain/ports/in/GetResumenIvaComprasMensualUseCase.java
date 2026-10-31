package eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.domain.ports.in;

import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.domain.model.ResumenIvaComprasMensual;

public interface GetResumenIvaComprasMensualUseCase {

    ResumenIvaComprasMensual getResumenIvaComprasMensual(Integer anho, Integer mes);

}
