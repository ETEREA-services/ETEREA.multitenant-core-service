package eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.web.mapper;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.web.dto.NegocioUnificadoResponse;
import org.springframework.stereotype.Component;

@Component
public class NegocioUnificadoDtoMapper {

    public NegocioUnificadoResponse toResponse(NegocioUnificado domain) {
        if (domain == null) {
            return null;
        }
        return NegocioUnificadoResponse.builder()
                .negocioId(domain.getNegocioId())
                .nombre(domain.getNombre())
                .databaseIp(domain.getDatabaseIp())
                .databasePort(domain.getDatabasePort())
                .databaseName(domain.getDatabaseName())
                .databaseUsername(domain.getDatabaseUsername())
                .backup(domain.getBackup())
                .tenantId(domain.getTenantId())
                .build();
    }

}
