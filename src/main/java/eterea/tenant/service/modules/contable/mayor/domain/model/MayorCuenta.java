package eterea.tenant.service.modules.contable.mayor.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Resultado completo del Mayor de una cuenta.
 */
public record MayorCuenta(
        long numeroCuenta,
        String nombre,
        BigDecimal saldoAnterior,
        List<MayorMovimiento> movimientos,
        BigDecimal totalDebe,
        BigDecimal totalHaber,
        BigDecimal saldoFinal,
        MayorControl control) {

    public MayorCuenta {
        nombre = nombre == null ? "" : nombre;
        saldoAnterior = saldoAnterior == null ? BigDecimal.ZERO : saldoAnterior;
        movimientos = movimientos == null ? List.of() : List.copyOf(movimientos);
        totalDebe = totalDebe == null ? BigDecimal.ZERO : totalDebe;
        totalHaber = totalHaber == null ? BigDecimal.ZERO : totalHaber;
        saldoFinal = saldoFinal == null ? BigDecimal.ZERO : saldoFinal;
        control = Objects.requireNonNull(control, "control");
    }

}
