package eterea.tenant.service.modules.reporte.api;

/**
 * Tipo de dato de una columna de reporte.
 *
 * <p>El PDF y la vista previa trabajan con las celdas ya formateadas; la
 * exportación XLSX usa este tipo para escribir números como números.</p>
 */
public enum ReporteTipoColumna {
    TEXTO,
    NUMERO
}
