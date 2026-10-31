package eterea.tenant.service.modules.contable.cuenta.domain.ports.in;

import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaNodo;

import java.util.List;

public interface FindCuentaArbolUseCase {
    List<CuentaNodo> findArbol();
}
