package eterea.tenant.service.modules.contable.cuenta.domain.ports.in;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;

import java.util.List;

public interface FindCuentasUseCase {
    List<Cuenta> findAll();
}
