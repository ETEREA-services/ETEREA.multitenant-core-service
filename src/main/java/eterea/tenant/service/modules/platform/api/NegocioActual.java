package eterea.tenant.service.modules.platform.api;

import java.util.Optional;

/**
 * Resuelve el negocio al que pertenece el request actual.
 *
 * <p>Contrato público para que otros módulos no dependan del modelo
 * {@code Negocio} ni de la tabla {@code negocio} de cada base de tenant.</p>
 */
public interface NegocioActual {

    Optional<Integer> findNegocioId();

    /**
     * Negocio activo con los datos mínimos para cabeceras de reportes.
     */
    Optional<NegocioActualInfo> findActual();

}
