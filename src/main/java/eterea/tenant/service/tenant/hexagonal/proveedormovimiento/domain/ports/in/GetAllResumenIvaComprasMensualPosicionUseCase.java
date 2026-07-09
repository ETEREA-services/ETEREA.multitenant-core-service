package eterea.tenant.service.tenant.hexagonal.proveedormovimiento.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.proveedormovimiento.domain.model.ResumenIvaComprasMensualPosicion;

import java.util.List;

public interface GetAllResumenIvaComprasMensualPosicionUseCase {

    List<ResumenIvaComprasMensualPosicion> getAllResumenIvaComprasMensualPosicion(Integer anho, Integer mes);

}
