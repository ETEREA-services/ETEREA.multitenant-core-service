package eterea.tenant.service.modules.contable.moneda.infrastructure.web.dto;

import eterea.tenant.service.modules.contable.moneda.domain.model.MonedaInfo;

import java.util.List;

/**
 * Catálogo de monedas del tenant para los selectores del reporte.
 */
public record MonedasResponse(List<MonedaInfo> monedas) {

    public MonedasResponse {
        monedas = List.copyOf(monedas);
    }

}
