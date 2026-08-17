package eterea.tenant.service.brain.negocio.infrastructure.web.mapper;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.infrastructure.web.dto.NegocioBrainRequest;
import eterea.tenant.service.brain.negocio.infrastructure.web.dto.NegocioBrainResponse;
import org.springframework.stereotype.Component;

@Component
public class NegocioBrainDtoMapper {

    public NegocioBrain toDomain(NegocioBrainRequest request) {
        if (request == null) {
            return null;
        }
        return NegocioBrain.builder()
                .negocioId(request.getNegocioId())
                .empresaId(request.getEmpresaId())
                .nombre(request.getNombre())
                .databaseName(request.getDatabaseName())
                .databaseIp(request.getDatabaseIp())
                .databasePort(request.getDatabasePort())
                .databaseUsername(request.getDatabaseUsername())
                .databasePassword(request.getDatabasePassword())
                .backendIp(request.getBackendIp())
                .backendPort(request.getBackendPort())
                .tenantId(request.getTenantId())
                .build();
    }

    public NegocioBrainResponse toResponse(NegocioBrain domain) {
        if (domain == null) {
            return null;
        }
        return NegocioBrainResponse.builder()
                .negocioId(domain.getNegocioId())
                .empresaId(domain.getEmpresaId())
                .nombre(domain.getNombre())
                .databaseName(domain.getDatabaseName())
                .databaseIp(domain.getDatabaseIp())
                .databasePort(domain.getDatabasePort())
                .databaseUsername(domain.getDatabaseUsername())
                .databasePassword(domain.getDatabasePassword())
                .backendIp(domain.getBackendIp())
                .backendPort(domain.getBackendPort())
                .tenantId(domain.getTenantId())
                .build();
    }
}
