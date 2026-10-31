package eterea.tenant.service.modules.contable.cuenta.domain.ports.in;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;

public interface CreateCuentaUseCase {
    Cuenta create(Cuenta cuenta);
}
