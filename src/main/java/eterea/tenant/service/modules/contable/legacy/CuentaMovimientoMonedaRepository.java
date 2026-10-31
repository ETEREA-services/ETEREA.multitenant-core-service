package eterea.tenant.service.modules.contable.legacy;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoMoneda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CuentaMovimientoMonedaRepository extends JpaRepository<CuentaMovimientoMoneda, UUID>, CuentaMovimientoMonedaRepositoryCustom {

}
