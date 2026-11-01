package eterea.tenant.service.modules.contable.balance.domain.model;

import java.math.BigDecimal;

/**
 * Fila del balance ya calculada.
 *
 * @param numeroCuenta    código de la cuenta
 * @param nombre          nombre visible
 * @param nivel           1..5, usado para la sangría
 * @param negrita         si la fila se imprime en negrita (cuentas de grado &lt; 5)
 * @param debe            débitos del subárbol (o propios en {@code POR_CUENTA})
 * @param haber           créditos del subárbol (o propios en {@code POR_CUENTA})
 * @param saldoDeudor     saldo neto deudor, redondeado a 2 decimales
 * @param saldoAcreedor   saldo neto acreedor, redondeado a 2 decimales
 */
public record BalanceCuenta(
        long numeroCuenta,
        String nombre,
        int nivel,
        boolean negrita,
        BigDecimal debe,
        BigDecimal haber,
        BigDecimal saldoDeudor,
        BigDecimal saldoAcreedor) {
}
