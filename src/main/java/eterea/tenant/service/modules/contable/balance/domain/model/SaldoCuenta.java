package eterea.tenant.service.modules.contable.balance.domain.model;

import java.math.BigDecimal;

/**
 * Agregado de debe/haber por cuenta devuelto por el puerto de movimientos.
 *
 * <p>No incluye el nombre ni la jerarquía: eso lo resuelve el calculador con el
 * plan de cuentas. Los importes ya vienen convertidos y redondeados a 5
 * decimales cuando corresponde.</p>
 */
public record SaldoCuenta(long numeroCuenta, BigDecimal debe, BigDecimal haber) {
}
