package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.ArticuloCentro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ArticuloCentroRepository extends JpaRepository<ArticuloCentro, Long> {

    List<ArticuloCentro> findAllByCentroStockIdAndArticuloIdIn(int centroStockId, List<String> articuloIds);

    Optional<ArticuloCentro> findByCentroStockIdAndArticuloId(int centroStockId, String articuloId);

}
