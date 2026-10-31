package eterea.tenant.service.modules.contable.cuenta.domain.exception;

/**
 * Error de validación del plan de cuentas.
 *
 * <p>El código es estable y lo consume el frontend para traducir el mensaje.
 */
public class CuentaInvalidaException extends RuntimeException {

    private final String codigo;

    public CuentaInvalidaException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
