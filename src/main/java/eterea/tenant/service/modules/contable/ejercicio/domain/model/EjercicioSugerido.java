package eterea.tenant.service.modules.contable.ejercicio.domain.model;

import java.time.LocalDate;

/**
 * Propuesta de próximo ejercicio calculada en el backend.
 *
 * <p>Cuando {@code disponible} es {@code false}, el motivo explica por qué no
 * corresponde crear otro ejercicio. No es un error HTTP: es un estado normal
 * de la consulta.</p>
 */
public record EjercicioSugerido(
        boolean disponible,
        String nombre,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String origen,
        String motivo) {
}
