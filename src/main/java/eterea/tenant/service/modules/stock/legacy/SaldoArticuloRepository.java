package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.SaldoArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface SaldoArticuloRepository extends JpaRepository<SaldoArticulo, String> {

    List<SaldoArticulo> findAllByCentroStockIdAndArticuloIdIn(int centroStockId, List<String> articuloIds);

    Optional<SaldoArticulo> findByCentroStockIdAndArticuloId(int centroStockId, String articuloId);

}
