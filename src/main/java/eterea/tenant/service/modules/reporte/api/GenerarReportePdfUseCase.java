package eterea.tenant.service.modules.reporte.api;

/**
 * Caso de uso transversal: renderizar cualquier {@link ReporteTabla} a PDF.
 */
public interface GenerarReportePdfUseCase {

    byte[] generar(ReporteTabla tabla);

}
