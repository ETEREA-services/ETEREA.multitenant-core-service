package eterea.tenant.service.modules.contable.legacy;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoAperturaMoneda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CuentaMovimientoAperturaMonedaRepository extends JpaRepository<CuentaMovimientoAperturaMoneda, UUID>, CuentaMovimientoAperturaMonedaRepositoryCustom {

}
