package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

/**
 * Filtros HTTP del Mayor General.
 */
public record MayorFiltroRequest(
        @Min(1) long cuenta,
        @NotNull LocalDate desde,
        @NotNull LocalDate hasta,
        Boolean sinSaldoAnterior,
        @Positive Integer monedaRegistroId,
        @Positive Integer monedaExpresionId) {

    public MayorFiltro toFiltro() {
        return new MayorFiltro(
                cuenta,
                desde,
                hasta,
                Boolean.TRUE.equals(sinSaldoAnterior),
                monedaRegistroId,
                monedaExpresionId);
    }

}
