package eterea.tenant.service.modules.contable.balance.domain.model;

import java.math.BigDecimal;

/**
 * Totales del balance.
 *
 * @param deudores   suma de saldos deudores
 * @param acreedores suma de saldos acreedores (positiva)
 * @param diferencia {@code deudores - acreedores}
 */
public record BalanceTotales(BigDecimal deudores, BigDecimal acreedores, BigDecimal diferencia) {
}
