package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.SaldoFecha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.List;

public interface SaldoFechaRepository extends JpaRepository<SaldoFecha, String> {

    List<SaldoFecha> findAllByCentroStockIdAndFechaAndArticuloIdIn(int centroStockId, OffsetDateTime fechaMovimiento, List<String> articuloIds);

    Optional<SaldoFecha> findByCentroStockIdAndArticuloIdAndFecha(int centroStockId, String articuloId, OffsetDateTime fechaMovimiento);

}
