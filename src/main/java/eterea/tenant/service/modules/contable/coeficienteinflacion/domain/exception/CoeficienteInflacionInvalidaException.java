package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.exception;

/**
 * Error de validación de coeficientes de inflación.
 *
 * <p>El código es estable y lo consume el frontend para traducir el mensaje.</p>
 */
public class CoeficienteInflacionInvalidaException extends RuntimeException {

    private final String codigo;

    public CoeficienteInflacionInvalidaException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
