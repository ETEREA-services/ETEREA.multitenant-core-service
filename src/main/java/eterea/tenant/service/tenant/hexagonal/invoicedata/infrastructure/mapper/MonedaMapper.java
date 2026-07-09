package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto.MonedaResponse;
import eterea.tenant.service.tenant.kotlin.model.Moneda;
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
