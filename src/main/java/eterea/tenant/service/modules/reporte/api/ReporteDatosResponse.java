package eterea.tenant.service.modules.reporte.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Contrato JSON de un reporte para la vista previa.
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public record ReporteDatosResponse(
        String codigo,
        String titulo,
        String negocio,
        String generadoEn,
        List<ReporteColumnaResponse> columnas,
        List<ReporteFilaResponse> filas,
        int totalFilas) {

}
