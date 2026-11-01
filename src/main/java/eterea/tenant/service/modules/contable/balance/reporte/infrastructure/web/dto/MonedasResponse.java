package eterea.tenant.service.modules.contable.balance.reporte.infrastructure.web.dto;

import eterea.tenant.service.modules.contable.balance.domain.model.MonedaInfo;

import java.util.List;

/**
 * Catálogo de monedas del tenant para los selectores del reporte.
 */
public record MonedasResponse(List<MonedaInfo> monedas) {

    public MonedasResponse {
        monedas = List.copyOf(monedas);
    }

}
