package eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;
import java.util.List;

public interface FindAllCuentasWithCuentaMaestroUseCase {
    List<Cuenta> findAllWithCuentaMaestro();
}
