package eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.repository;

import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.dto.ResumenIvaComprasMensualDto;
import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.dto.ResumenIvaComprasMensualPosicionDto;
import eterea.tenant.service.modules.compras.legacy.hexagonal.proveedormovimiento.infrastructure.persistence.entity.ProveedorMovimientoEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaProveedorMovimientoRepository
        extends JpaRepository<ProveedorMovimientoEntity, Integer>, JpaProveedorMovimientoRepositoryCustom {

    List<ProveedorMovimientoEntity> findAllByProveedorIdOrderByFechaComprobante(Long proveedorId);

    Optional<ProveedorMovimientoEntity> findByProveedorMovimientoId(Long proveedorMovimientoId);

}
