package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.dto;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

/**
 * Filtros HTTP del Mayor Discriminado.
 */
public record MayorDiscriminadoFiltroRequest(
        @NotEmpty List<@Positive Long> cuentas,
        @NotNull LocalDate desde,
        @NotNull LocalDate hasta,
        @Positive Integer monedaRegistroId,
        @Positive Integer monedaExpresionId) {

    public MayorDiscriminadoFiltro toFiltro() {
        return new MayorDiscriminadoFiltro(
                cuentas,
                desde,
                hasta,
                monedaRegistroId,
                monedaExpresionId);
    }

}
