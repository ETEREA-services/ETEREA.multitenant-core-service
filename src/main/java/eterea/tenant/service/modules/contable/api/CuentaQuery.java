package eterea.tenant.service.modules.contable.api;

import java.util.Optional;

/**
 * Consulta de cuentas contables para otros módulos.
 */
public interface CuentaQuery {

    Optional<CuentaResumen> findByNumeroCuenta(Long numeroCuenta);

}
