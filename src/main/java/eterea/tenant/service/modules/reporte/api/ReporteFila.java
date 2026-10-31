package eterea.tenant.service.modules.reporte.api;

import java.util.List;
import java.util.Objects;

/**
 * Fila de un reporte.
 *
 * @param nivel   1..5; se usa para la sangría de las columnas que la admiten
 * @param negrita si la fila se imprime en negrita
 * @param celdas  valores alineados con {@link ReporteTabla#columnas()}
 */
public record ReporteFila(int nivel, boolean negrita, List<String> celdas) {

    public ReporteFila {
        if (nivel < 1) {
            throw new IllegalArgumentException("nivel debe ser mayor o igual a 1");
        }
        celdas = List.copyOf(Objects.requireNonNull(celdas, "celdas"));
    }

}
