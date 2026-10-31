package eterea.tenant.service.modules.reporte.api;

import java.util.List;
import java.util.Objects;

/**
 * Tabla completa de un reporte: cabecera, columnas y filas.
 *
 * <p>Es la única fuente de verdad: el JSON de vista previa y el PDF se generan
 * a partir de este mismo objeto.</p>
 */
public record ReporteTabla(
        ReporteCabecera cabecera,
        List<ReporteColumna> columnas,
        List<ReporteFila> filas) {

    public ReporteTabla {
        cabecera = Objects.requireNonNull(cabecera, "cabecera");
        columnas = List.copyOf(Objects.requireNonNull(columnas, "columnas"));
        filas = List.copyOf(Objects.requireNonNull(filas, "filas"));
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

    public int totalFilas() {
        return filas.size();
    }

}
