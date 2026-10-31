package eterea.tenant.service.modules.reporte.api;

import java.util.List;

/**
 * Fila del contrato JSON.
 */
public record ReporteFilaResponse(
        int nivel,
        boolean negrita,
        List<String> celdas) {

}
