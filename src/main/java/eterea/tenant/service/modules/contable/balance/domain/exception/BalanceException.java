package eterea.tenant.service.modules.contable.balance.domain.exception;

/**
 * Error de negocio del balance.
 *
 * <p>El código es estable y lo traduce el controller a un HTTP concreto.</p>
 */
public class BalanceException extends RuntimeException {

    private final String codigo;

    public BalanceException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public BalanceException(String codigo, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
