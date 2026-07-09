package eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.mapper;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.entity.NegocioUnificadoEntity;
import org.springframework.stereotype.Component;

@Component
public class NegocioUnificadoMapper {

    public NegocioUnificado toDomain(NegocioUnificadoEntity entity) {
        if (entity == null) {
            return null;
        }
        return NegocioUnificado.builder()
                .negocioId(entity.getNegocioId())
                .nombre(entity.getNombre())
                .databaseIp(entity.getDatabaseIp())
                .databaseName(entity.getDatabaseName())
                .databaseUsername(entity.getDatabaseUsername())
                .backup(entity.getBackup())
                .backendServer(entity.getBackendServer())
                .backendPort(entity.getBackendPort())
                .tenantId(entity.getTenantId())
                .build();
    }

    public NegocioUnificadoEntity toEntity(NegocioUnificado domain) {
        if (domain == null) {
            return null;
        }
        return NegocioUnificadoEntity.builder()
                .negocioId(domain.getNegocioId())
                .nombre(domain.getNombre())
                .databaseIp(domain.getDatabaseIp())
                .databaseName(domain.getDatabaseName())
                .databaseUsername(domain.getDatabaseUsername())
                .backup(domain.getBackup())
                .backendServer(domain.getBackendServer())
                .backendPort(domain.getBackendPort())
                .tenantId(domain.getTenantId())
                .build();
    }

}
