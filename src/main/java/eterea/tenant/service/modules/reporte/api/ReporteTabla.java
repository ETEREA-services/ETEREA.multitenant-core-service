package eterea.tenant.service.modules.reporte.api;

import java.util.List;
import java.util.Objects;

/**
 * Tabla completa de un reporte: cabecera, columnas, filas y advertencias.
 *
 * <p>Es la única fuente de verdad: el JSON de vista previa y el PDF se generan
 * a partir de este mismo objeto.</p>
 */
public record ReporteTabla(
        ReporteCabecera cabecera,
        List<ReporteColumna> columnas,
        List<ReporteFila> filas,
        List<String> advertencias) {

    public ReporteTabla {
        cabecera = Objects.requireNonNull(cabecera, "cabecera");
        columnas = List.copyOf(Objects.requireNonNull(columnas, "columnas"));
        filas = List.copyOf(Objects.requireNonNull(filas, "filas"));
        advertencias = advertencias == null ? List.of() : List.copyOf(advertencias);
        if (columnas.isEmpty()) {
            throw new IllegalArgumentException("El reporte debe tener al menos una columna");
        }
        for (ReporteFila fila : filas) {
            if (fila.celdas().size() != columnas.size()) {
                throw new IllegalArgumentException(
                        "Cada fila debe tener una celda por columna");
            }
        }
    }

    /**
     * Constructor de conveniencia para los reportes sin advertencias.
     */
    public ReporteTabla(
            ReporteCabecera cabecera,
            List<ReporteColumna> columnas,
            List<ReporteFila> filas) {
        this(cabecera, columnas, filas, List.of());
    }

    public int totalFilas() {
        return filas.size();
    }

}
