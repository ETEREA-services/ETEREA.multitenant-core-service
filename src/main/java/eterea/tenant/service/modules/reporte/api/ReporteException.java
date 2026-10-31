package eterea.tenant.service.modules.reporte.api;

/**
 * Error de negocio o de generación de un reporte.
 */
public class ReporteException extends RuntimeException {

    private final String codigo;

    public ReporteException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public ReporteException(String codigo, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
