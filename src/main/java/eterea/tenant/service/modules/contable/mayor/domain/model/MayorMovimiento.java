package eterea.tenant.service.modules.contable.mayor.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Movimiento del Mayor con el saldo corrido ya calculado.
 */
public record MayorMovimiento(
        LocalDate fecha,
        String comprobante,
        String concepto,
        Integer asiento,
        BigDecimal debe,
        BigDecimal haber,
        BigDecimal saldo,
        String proveedor) {

    public MayorMovimiento {
        fecha = Objects.requireNonNull(fecha, "fecha");
        comprobante = comprobante == null ? "" : comprobante;
        concepto = concepto == null ? "" : concepto;
        debe = debe == null ? BigDecimal.ZERO : debe;
        haber = haber == null ? BigDecimal.ZERO : haber;
        saldo = saldo == null ? BigDecimal.ZERO : saldo;
        proveedor = proveedor == null ? "" : proveedor;
    }

}
