package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByFechaAndInventarioTurnoIdAndCentroStockIdAndArticuloId(OffsetDateTime fecha, int inventarioTurnoId, int centroStockId, String articuloId);

}
