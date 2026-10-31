package eterea.tenant.service.modules.reporte.api;

/**
 * Columna del contrato JSON.
 */
public record ReporteColumnaResponse(
        String clave,
        String titulo,
        ReporteAlineacion alineacion,
        float anchoRelativo,
        boolean admiteSangria) {

}
