package eterea.tenant.service.modules.contable.mayor.domain.exception;

/**
 * Error de negocio del Mayor.
 *
 * <p>El código es estable y lo traducirá el controller a un HTTP concreto.</p>
 */
public class MayorException extends RuntimeException {

    private final String codigo;

    public MayorException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public MayorException(String codigo, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
