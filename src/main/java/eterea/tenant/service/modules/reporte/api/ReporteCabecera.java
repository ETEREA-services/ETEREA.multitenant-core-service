package eterea.tenant.service.modules.reporte.api;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Cabecera común de todos los reportes.
 *
 * @param codigo    identificador estable del reporte
 * @param titulo    título principal
 * @param negocio   nombre del negocio activo
 * @param fecha     fecha de generación
 * @param subtitulo línea opcional debajo del título (período, monedas, etc.)
 */
public record ReporteCabecera(
        String codigo,
        String titulo,
        String negocio,
        LocalDate fecha,
        String subtitulo) {

    public ReporteCabecera {
        codigo = Objects.requireNonNull(codigo, "codigo");
        titulo = Objects.requireNonNull(titulo, "titulo");
        negocio = negocio == null ? "" : negocio;
        fecha = Objects.requireNonNull(fecha, "fecha");
        subtitulo = subtitulo == null ? "" : subtitulo;
    }

    /**
     * Constructor de conveniencia para los reportes que no usan subtítulo.
     */
    public ReporteCabecera(String codigo, String titulo, String negocio, LocalDate fecha) {
        this(codigo, titulo, negocio, fecha, "");
    }

}
