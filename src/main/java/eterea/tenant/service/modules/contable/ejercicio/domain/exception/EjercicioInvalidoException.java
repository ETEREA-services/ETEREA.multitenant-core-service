package eterea.tenant.service.modules.contable.ejercicio.domain.exception;

/**
 * Error de validación de un ejercicio.
 *
 * <p>El código es estable y lo consume el frontend para traducir el mensaje.</p>
 */
public class EjercicioInvalidoException extends RuntimeException {

    private final String codigo;

    public EjercicioInvalidoException(String codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
