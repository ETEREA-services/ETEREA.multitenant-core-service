package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.StockMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StockMovimientoRepository extends JpaRepository<StockMovimiento, Long> {

    Optional<StockMovimiento> findFirstByComprobanteIdOrderByStockMovimientoIdDesc(int comprobanteId);

    Optional<StockMovimiento> findByStockMovimientoId(long stockMovimientoId);

}
