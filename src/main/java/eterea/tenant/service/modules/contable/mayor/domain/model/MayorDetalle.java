package eterea.tenant.service.modules.contable.mayor.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Fila cruda del libro para una cuenta, sin saldo corrido.
 *
 * <p>Los importes ya vienen convertidos (5 decimales) cuando corresponde; el
 * calculador los redondea a 2 decimales para la presentación.</p>
 */
public record MayorDetalle(
        LocalDate fecha,
        Integer comprobanteId,
        String comprobante,
        String concepto,
        Integer asiento,
        BigDecimal debe,
        BigDecimal haber,
        Long proveedorId,
        String proveedor) {

    public MayorDetalle {
        fecha = Objects.requireNonNull(fecha, "fecha");
        comprobante = comprobante == null ? "" : comprobante;
        concepto = concepto == null ? "" : concepto;
        debe = debe == null ? BigDecimal.ZERO : debe;
        haber = haber == null ? BigDecimal.ZERO : haber;
        proveedorId = proveedorId == null ? 0L : proveedorId;
        proveedor = proveedor == null ? "" : proveedor;
    }

}
