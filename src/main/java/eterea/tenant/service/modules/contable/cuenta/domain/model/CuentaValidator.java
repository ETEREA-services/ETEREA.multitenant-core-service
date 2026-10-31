package eterea.tenant.service.modules.contable.cuenta.domain.model;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;

import java.math.BigDecimal;

/**
 * Validaciones de los datos editables de una cuenta.
 */
public final class CuentaValidator {

    private static final int NOMBRE_MAXIMO = 200;
    private static final BigDecimal CUENTA_MAESTRO_MAXIMA = new BigDecimal("99999999999");

    private CuentaValidator() {
    }

    public static void validarNumeroCuenta(Long numeroCuenta) {
        if (numeroCuenta == null) {
            throw new CuentaInvalidaException(
                    "CUENTA_CODIGO_INVALIDO",
                    "El código debe ser numérico, de 1 a 8 dígitos y distinto de cero.");
        }
    }

    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new CuentaInvalidaException(
                    "CUENTA_NOMBRE_REQUERIDO", "Ingresá el nombre de la cuenta.");
        }
        if (nombre.trim().length() > NOMBRE_MAXIMO) {
            throw new CuentaInvalidaException(
                    "CUENTA_NOMBRE_INVALIDO",
                    "El nombre no puede superar los " + NOMBRE_MAXIMO + " caracteres.");
        }
    }

    public static byte flag(Byte valor) {
        return valor != null && valor == 1 ? (byte) 1 : (byte) 0;
    }

    public static void validarCuentaMaestro(BigDecimal cuentaMaestro) {
        if (cuentaMaestro == null) {
            return;
        }
        boolean entero = cuentaMaestro.stripTrailingZeros().scale() <= 0;
        if (cuentaMaestro.signum() < 0
                || !entero
                || cuentaMaestro.compareTo(CUENTA_MAESTRO_MAXIMA) > 0) {
            throw new CuentaInvalidaException(
                    "CUENTA_MAESTRO_INVALIDA",
                    "La cuenta maestra debe ser un número entero entre 0 y 99999999999.");
        }
    }
}
