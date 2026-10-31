package eterea.tenant.service.modules.contable.ejercicio.domain.ports.out;

import java.time.LocalDate;

/**
 * Consulta de bloqueo por fecha.
 *
 * <p>La consumirán los casos de uso de asientos cuando se migren. En esta etapa
 * deja la regla disponible en el dominio y evita que el bloqueo sea un flag
 * decorativo.</p>
 */
public interface EjercicioBloqueoPort {

    boolean estaBloqueado(LocalDate fecha);

}
