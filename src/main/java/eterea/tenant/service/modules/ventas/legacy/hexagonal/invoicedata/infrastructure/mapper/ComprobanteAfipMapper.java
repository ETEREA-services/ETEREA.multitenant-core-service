package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.dto.ComprobanteAfipResponse;
import eterea.tenant.service.modules.ventas.legacy.ComprobanteAfip;
import org.springframework.stereotype.Component;

@Component
public class ComprobanteAfipMapper {

    public ComprobanteAfipResponse toResponse(ComprobanteAfip comprobanteAfip) {
        if (comprobanteAfip == null) {
            return null;
        }
        return ComprobanteAfipResponse.builder()
                .comprobanteAfipId(comprobanteAfip.getComprobanteAfipId())
                .label(comprobanteAfip.getLabel())
                .build();
    }

}
