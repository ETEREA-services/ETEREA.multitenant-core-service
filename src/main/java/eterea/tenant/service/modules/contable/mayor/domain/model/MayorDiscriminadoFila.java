package eterea.tenant.service.modules.contable.mayor.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Fila del Mayor Discriminado: movimientos de varias cuentas sin saldo corrido.
 */
public record MayorDiscriminadoFila(
        LocalDate fecha,
        long numeroCuenta,
        String nombreCuenta,
        String comprobante,
        String concepto,
        Integer asiento,
        BigDecimal debe,
        BigDecimal haber,
        String proveedor) {

    public MayorDiscriminadoFila {
        fecha = Objects.requireNonNull(fecha, "fecha");
        nombreCuenta = nombreCuenta == null ? "" : nombreCuenta;
        comprobante = comprobante == null ? "" : comprobante;
        concepto = concepto == null ? "" : concepto;
        debe = debe == null ? BigDecimal.ZERO : debe;
        haber = haber == null ? BigDecimal.ZERO : haber;
        proveedor = proveedor == null ? "" : proveedor;
    }

}
