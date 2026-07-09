package eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.web.mapper;

import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.AnticipoHaberes;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.CierreCajaAnticipoHaberes;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.web.dto.AnticipoHaberesRequest;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.web.dto.AnticipoHaberesResponse;
import org.springframework.stereotype.Component;

@Component
public class AnticipoHaberesDtoMapper {

    public AnticipoHaberes toData(AnticipoHaberesRequest anticipoHaberesRequest) {
        if (anticipoHaberesRequest == null) {
            return null;
        }
        return AnticipoHaberes.builder()
                .legajoId(anticipoHaberesRequest.getLegajoId())
                .fecha(anticipoHaberesRequest.getFecha())
                .importe(anticipoHaberesRequest.getImporte())
                .build();
    }

    public AnticipoHaberesResponse toResponse(CierreCajaAnticipoHaberes cierreCajaAnticipoHaberes) {
        if (cierreCajaAnticipoHaberes == null) {
            return null;
        }
        return AnticipoHaberesResponse.builder()
                .legajoId(cierreCajaAnticipoHaberes.getLegajoId())
                .importe(cierreCajaAnticipoHaberes.getImporte())
                .build();
    }

}
