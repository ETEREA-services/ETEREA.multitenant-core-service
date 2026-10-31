package eterea.tenant.service.modules.contable.legacy;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoFirma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface CuentaMovimientoFirmaRepository extends JpaRepository<CuentaMovimientoFirma, Long> {

    Optional<CuentaMovimientoFirma> findByFechaContableAndOrdenContable(OffsetDateTime fechaContable, int ordenContable);

}
