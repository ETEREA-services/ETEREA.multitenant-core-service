package eterea.tenant.service.brain.empresa.infrastructure.web.mapper;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.infrastructure.web.dto.EmpresaBrainRequest;
import eterea.tenant.service.brain.empresa.infrastructure.web.dto.EmpresaBrainResponse;
import org.springframework.stereotype.Component;

@Component
public class EmpresaBrainDtoMapper {

    public EmpresaBrain toDomain(EmpresaBrainRequest request) {
        if (request == null) {
            return null;
        }
        return EmpresaBrain.builder()
                .nombre(request.getNombre())
                .build();
    }

    public EmpresaBrainResponse toResponse(EmpresaBrain domain) {
        if (domain == null) {
            return null;
        }
        return EmpresaBrainResponse.builder()
                .empresaId(domain.getEmpresaId())
                .nombre(domain.getNombre())
                .build();
    }
}
