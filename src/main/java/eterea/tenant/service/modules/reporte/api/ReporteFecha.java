package eterea.tenant.service.modules.reporte.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Formato único de fechas visibles para reportes y PDF.
 */
public final class ReporteFecha {

    public static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private ReporteFecha() {
    }

    public static String formatear(LocalDate fecha) {
        return fecha == null ? "" : fecha.format(FORMATO);
    }

}
