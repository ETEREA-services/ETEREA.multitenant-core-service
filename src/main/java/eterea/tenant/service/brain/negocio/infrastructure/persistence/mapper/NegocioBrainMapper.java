package eterea.tenant.service.brain.negocio.infrastructure.persistence.mapper;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.infrastructure.persistence.entity.NegocioBrainEntity;
import org.springframework.stereotype.Component;

@Component
public class NegocioBrainMapper {

    public NegocioBrain toDomain(NegocioBrainEntity entity) {
        if (entity == null) {
            return null;
        }
        return NegocioBrain.builder()
                .negocioId(entity.getNegocioId())
                .empresaId(entity.getEmpresaId())
                .nombre(entity.getNombre())
                .databaseName(entity.getDatabaseName())
                .databaseIp(entity.getDatabaseIp())
                .databasePort(entity.getDatabasePort())
                .databaseUsername(entity.getDatabaseUsername())
                .databasePassword(entity.getDatabasePassword())
                .backendIp(entity.getBackendIp())
                .backendPort(entity.getBackendPort())
                .tenantId(entity.getTenantId())
                .build();
    }

    public NegocioBrainEntity toEntity(NegocioBrain domain) {
        if (domain == null) {
            return null;
        }
        return NegocioBrainEntity.builder()
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
