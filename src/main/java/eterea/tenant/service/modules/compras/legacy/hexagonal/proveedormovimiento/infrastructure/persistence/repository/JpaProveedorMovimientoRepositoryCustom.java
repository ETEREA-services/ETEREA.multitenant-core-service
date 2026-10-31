package eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.repository;

import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.dto.ResumenIvaComprasMensualDto;
import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.dto.ResumenIvaComprasMensualPosicionDto;

import java.util.List;

public interface JpaProveedorMovimientoRepositoryCustom {

    ResumenIvaComprasMensualDto findResumenByYearAndMonth(Integer year, Integer month);

    List<ResumenIvaComprasMensualPosicionDto> findAllResumenPosicionByYearAndMonth(Integer year, Integer month);

    List<eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.entity.ProveedorMovimientoEntity>
            findAllByFechaContableBetweenAndComprobanteLibroIva(
                    java.time.OffsetDateTime desde,
                    java.time.OffsetDateTime hasta,
                    Byte libroIva,
                    org.springframework.data.domain.Sort sort);

}
