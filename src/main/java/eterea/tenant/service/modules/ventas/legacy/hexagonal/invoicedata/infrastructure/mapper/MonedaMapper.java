package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.dto.MonedaResponse;
import eterea.tenant.service.modules.platform.legacy.Moneda;
import org.springframework.stereotype.Component;

@Component
public class MonedaMapper {

    public MonedaResponse toResponse(Moneda moneda) {
        if (moneda == null) {
            return null;
        }
        return MonedaResponse.builder()
                .simbolo(moneda.getSimbolo())
                .build();
    }

}
