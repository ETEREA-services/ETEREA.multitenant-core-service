package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Coeficiente de un mes dentro de la vista anual.
 *
 * <p>{@code coeficiente == null} significa que el mes todavía no fue cargado.
 * No debe confundirse con el valor cero.</p>
 */
public record CoeficienteMensual(
        int mes,
        BigDecimal coeficiente,
        Long coeficienteInflacionId,
        LocalDateTime created) {

    public boolean cargado() {
        return coeficiente != null;
    }

}
