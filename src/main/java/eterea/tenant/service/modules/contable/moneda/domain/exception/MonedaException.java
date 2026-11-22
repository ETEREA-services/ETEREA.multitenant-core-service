package eterea.tenant.service.modules.contable.moneda.domain.exception;

/**
 * Error de negocio del catálogo de monedas y de las cotizaciones.
 *
 * <p>Los slices que lo consumen (balance, mayor) lo traducen a sus propios
 * códigos públicos para no acoplar sus contratos HTTP al módulo de moneda.</p>
 */
public class MonedaException extends RuntimeException {

    private final String codigo;

    public MonedaException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public MonedaException(String codigo, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
