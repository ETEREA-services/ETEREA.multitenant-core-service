package eterea.tenant.service.modules.reporte.domain.ports.out;

import eterea.tenant.service.modules.reporte.api.ReporteTabla;

/**
 * Puerto de salida del renderizado de reportes.
 */
public interface ReportePdfPort {

    byte[] generar(ReporteTabla tabla);

}
