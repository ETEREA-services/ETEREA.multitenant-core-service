package eterea.tenant.service.modules.contable.ejercicio.domain.model;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;

import java.time.LocalDate;

/**
 * Validaciones de los datos editables de un ejercicio.
 */
public final class EjercicioValidator {

    public static final int NOMBRE_MAXIMO = 128;

    private EjercicioValidator() {
    }

    public static void validarId(Long ejercicioId) {
        if (ejercicioId == null || ejercicioId <= 0) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_NO_EXISTE", "El ejercicio indicado no existe.");
        }
    }

    public static String normalizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_NOMBRE_REQUERIDO", "Ingresá el nombre del ejercicio.");
        }
        String limpio = nombre.trim();
        if (limpio.length() > NOMBRE_MAXIMO) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_NOMBRE_INVALIDO",
                    "El nombre no puede superar los " + NOMBRE_MAXIMO + " caracteres.");
        }
        return limpio;
    }

    public static void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_FECHAS_REQUERIDAS",
                    "Ingresá la fecha de inicio y la fecha de fin del ejercicio.");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_FECHAS_INVALIDAS",
                    "La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
    }

}
