package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto.ComprobanteResponse;
import eterea.tenant.service.tenant.hexagonal.comprobante.infrastructure.persistence.entity.ComprobanteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComprobanteResponseMapper {

    private final ComprobanteAfipMapper comprobanteAfipMapper;

    public ComprobanteResponse toResponse(ComprobanteEntity comprobante) {
        if (comprobante == null) {
            return null;
        }
        return ComprobanteResponse.builder()
                .letraComprobante(comprobante.getLetraComprobante())
                .contado(comprobante.getContado())
                .comprobanteAfip(comprobanteAfipMapper.toResponse(comprobante.getComprobanteAfip()))
                .build();
    }

}
