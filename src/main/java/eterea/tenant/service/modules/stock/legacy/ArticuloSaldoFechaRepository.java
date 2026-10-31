package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.ArticuloSaldoFecha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.List;

public interface ArticuloSaldoFechaRepository extends JpaRepository<ArticuloSaldoFecha, Long>, ArticuloSaldoFechaRepositoryCustom {

    List<ArticuloSaldoFecha> findAllByCentroStockIdAndFechaAndArticuloIdIn(int centroStockId, OffsetDateTime fechaMovimiento, List<String> articuloIds);

    Optional<ArticuloSaldoFecha> findByCentroStockIdAndArticuloIdAndFecha(int centroStockId, String articuloId, OffsetDateTime fechaMovimiento);

}
