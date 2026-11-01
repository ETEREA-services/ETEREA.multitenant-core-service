package eterea.tenant.service.modules.reporte.api;

/**
 * Caso de uso transversal: exportar cualquier {@link ReporteTabla} a XLSX.
 */
public interface GenerarReporteXlsxUseCase {

    byte[] generar(ReporteTabla tabla);

}
