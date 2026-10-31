package eterea.tenant.service.modules.ventas.legacy;

import eterea.tenant.service.modules.ventas.legacy.FacturaRendicionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FacturaRendicionDetalleRepository extends JpaRepository<FacturaRendicionDetalle, Long> {

    List<FacturaRendicionDetalle> findAllByProveedorId(long proveedorId);

}
