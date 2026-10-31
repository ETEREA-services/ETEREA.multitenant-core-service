package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.ArticuloFecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public interface ArticuloFechaRepository extends JpaRepository<ArticuloFecha, Long> {

    Optional<ArticuloFecha> findByArticuloIdAndFecha(String articuloId, OffsetDateTime fecha);

    Optional<ArticuloFecha> findByArticuloFechaId(Long articuloFechaId);

    List<ArticuloFecha> findAllByArticuloIdAndFechaBetween(String articuloId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin);

}
