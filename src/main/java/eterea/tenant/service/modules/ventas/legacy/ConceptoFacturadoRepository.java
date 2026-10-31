package eterea.tenant.service.modules.ventas.legacy;

import eterea.tenant.service.modules.ventas.legacy.ConceptoFacturado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConceptoFacturadoRepository extends JpaRepository<ConceptoFacturado, Long> {

    Optional<ConceptoFacturado> findTopByArticuloMovimientoId(long articuloMovimientoId);

}
