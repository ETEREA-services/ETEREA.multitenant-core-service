package eterea.tenant.service.modules.contable.cuenta.domain.ports.out;

import java.util.Optional;

/**
 * Resuelve el negocio legacy de la base del tenant activo.
 */
public interface NegocioActualPort {
    Optional<Integer> findNegocioId();
}
