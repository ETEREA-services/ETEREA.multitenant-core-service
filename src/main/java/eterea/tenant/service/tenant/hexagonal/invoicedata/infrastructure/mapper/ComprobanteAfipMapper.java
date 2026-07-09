package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto.ComprobanteAfipResponse;
import eterea.tenant.service.tenant.kotlin.model.ComprobanteAfip;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ComprobanteAfipMapper {

    public ComprobanteAfipResponse toResponse(@Nullable ComprobanteAfip comprobanteAfip) {
        if (comprobanteAfip == null) {
            return null;
        }
        return ComprobanteAfipResponse.builder()
                .comprobanteAfipId(comprobanteAfip.getComprobanteAfipId())
                .label(comprobanteAfip.getLabel())
                .build();
    }

}
