package eterea.tenant.service.modules.contable.ejercicio.domain.model;

import java.time.LocalDate;

/**
 * Estado de un ejercicio respecto de la fecha actual.
 *
 * <p>Es un dato derivado: no se persiste. El estado de bloqueo es independiente
 * y se expone en {@link Ejercicio#isBloqueado()}.</p>
 */
public enum EstadoEjercicio {

    FUTURO,
    EN_CURSO,
    FINALIZADO;

    public static EstadoEjercicio de(LocalDate fechaInicio, LocalDate fechaFin, LocalDate hoy) {
        if (hoy.isBefore(fechaInicio)) {
            return FUTURO;
        }
        if (hoy.isAfter(fechaFin)) {
            return FINALIZADO;
        }
        return EN_CURSO;
    }

}
