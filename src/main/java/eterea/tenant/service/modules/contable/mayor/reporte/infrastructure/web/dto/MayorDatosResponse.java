package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto;

import eterea.tenant.service.modules.reporte.api.ReporteDatosResponse;

/**
 * Respuesta de la vista previa del Mayor: reporte y controles.
 */
public record MayorDatosResponse(
        ReporteDatosResponse reporte,
        MayorControlResponse control) {
}
