package eterea.tenant.service.modules.contable.cuenta.domain.ports.in;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import java.math.BigDecimal;

public interface FindCuentaByCuentaMaestroUseCase {
    Cuenta findByCuentaMaestro(BigDecimal cuentaMaestro);
}
