package eterea.tenant.service.modules.reporte.domain.ports.out;

import eterea.tenant.service.modules.reporte.api.ReporteTabla;

/**
 * Puerto de salida de la exportación XLSX de reportes.
 */
public interface ReporteXlsxPort {

    byte[] generar(ReporteTabla tabla);

}
