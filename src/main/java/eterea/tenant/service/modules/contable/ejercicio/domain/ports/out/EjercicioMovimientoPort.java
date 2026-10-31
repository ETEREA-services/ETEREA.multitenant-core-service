package eterea.tenant.service.modules.contable.ejercicio.domain.ports.out;

import java.time.LocalDate;

/**
 * Consulta de movimientos contables asociados a un ejercicio.
 *
 * <p>La relación principal es {@code movcon.ejercicio_id}. Para datos viejos
 * sin ese campo se consulta también el rango de fechas.</p>
 */
public interface EjercicioMovimientoPort {

    boolean existeMovimientoEnEjercicio(Long ejercicioId);

    boolean existeMovimientoEnRango(LocalDate fechaDesde, LocalDate fechaHasta);

}
