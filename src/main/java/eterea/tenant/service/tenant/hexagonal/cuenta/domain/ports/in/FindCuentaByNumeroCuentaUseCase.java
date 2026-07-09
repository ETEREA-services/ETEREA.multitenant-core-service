package eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;

public interface FindCuentaByNumeroCuentaUseCase {
    Cuenta findByNumeroCuenta(Long numeroCuenta);
}
