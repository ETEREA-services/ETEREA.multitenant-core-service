package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model;

import java.math.BigDecimal;

/**
 * Cambio solicitado para un mes.
 *
 * <p>{@code coeficiente == null} indica que el mes debe limpiarse.</p>
 */
public record CoeficienteMensualCambio(int mes, BigDecimal coeficiente) {
}
