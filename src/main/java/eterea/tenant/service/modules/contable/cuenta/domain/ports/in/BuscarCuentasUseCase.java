package eterea.tenant.service.modules.contable.cuenta.domain.ports.in;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;

import java.util.List;

/**
 * Búsqueda legacy de cuentas para el selector del Mayor.
 */
public interface BuscarCuentasUseCase {

    List<Cuenta> buscar(String texto);

}
