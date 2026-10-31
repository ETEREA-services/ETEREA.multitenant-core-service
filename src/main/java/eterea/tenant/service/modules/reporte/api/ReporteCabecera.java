package eterea.tenant.service.modules.reporte.api;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Cabecera común de todos los reportes.
 *
 * @param codigo  identificador estable del reporte
 * @param titulo  título principal
 * @param negocio nombre del negocio activo
 * @param fecha   fecha de generación
 */
public record ReporteCabecera(
        String codigo,
        String titulo,
        String negocio,
        LocalDate fecha) {

    public ReporteCabecera {
        codigo = Objects.requireNonNull(codigo, "codigo");
        titulo = Objects.requireNonNull(titulo, "titulo");
        negocio = negocio == null ? "" : negocio;
        fecha = Objects.requireNonNull(fecha, "fecha");
    }

}
