package eterea.tenant.service.modules.contable.balance.reporte.infrastructure.web.dto;

import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceModo;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Filtros del reporte de balance.
 *
 * <p>Las monedas se eligen en cada request; si no se envían, el dominio usa la
 * moneda base (ARS). No hay configuración persistida por tenant.</p>
 */
public record BalanceFiltroRequest(
        @NotNull LocalDate desde,
        @NotNull LocalDate hasta,
        Boolean incluyeApertura,
        Boolean incluyeInflacion,
        BalanceModo modo,
        Integer monedaRegistroId,
        Integer monedaExpresionId) {

    public BalanceFiltro toFiltro() {
        return new BalanceFiltro(
                desde,
                hasta,
                Boolean.TRUE.equals(incluyeApertura),
                incluyeInflacion == null || incluyeInflacion,
                modo == null ? BalanceModo.ESTRUCTURA : modo,
                monedaRegistroId,
                monedaExpresionId);
    }

}
