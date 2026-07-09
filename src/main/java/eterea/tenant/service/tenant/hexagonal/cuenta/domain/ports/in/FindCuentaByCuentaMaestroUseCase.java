package eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;
import java.math.BigDecimal;

public interface FindCuentaByCuentaMaestroUseCase {
    Cuenta findByCuentaMaestro(BigDecimal cuentaMaestro);
}
