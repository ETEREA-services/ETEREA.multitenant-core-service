package eterea.tenant.service.modules.tesoreria.legacy;

import eterea.tenant.service.modules.tesoreria.legacy.ValorMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface ValorMovimientoRepository extends JpaRepository<ValorMovimiento, Integer>, ValorMovimientoRepositoryCustom {

    Optional<ValorMovimiento> findByValorMovimientoId(long valorMovimientoId);

    List<ValorMovimiento> findAllByFechaContableAndOrdenContable(OffsetDateTime fechaContable, int ordenContable);

}
