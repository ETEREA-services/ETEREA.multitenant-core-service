package eterea.tenant.service.tenant.hexagonal.negocio.infrastructure.persistence.mapper;

import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;
import eterea.tenant.service.tenant.hexagonal.negocio.infrastructure.persistence.entity.NegocioEntity;
import org.springframework.stereotype.Component;

@Component
public class NegocioMapper {

    public Negocio toDomainModel(NegocioEntity entity) {
        if (entity == null) {
            return null;
        }
        return Negocio.builder()
                .negocioId(entity.getNegocioId())
                .nombre(entity.getNombre())
                .negocioIdReal(entity.getNegocioIdReal())
                .databaseIpVpn(entity.getDatabaseIpVpn())
                .databaseIpLan(entity.getDatabaseIpLan())
                .databasePort(entity.getDatabasePort())
                .database(entity.getDatabase())
                .user(entity.getUser())
                .transferenciaStock(entity.getTransferenciaStock())
                .transferenciaValor(entity.getTransferenciaValor())
                .backendIpVpn(entity.getBackendIpVpn())
                .backendIpLan(entity.getBackendIpLan())
                .backendPort(entity.getBackendPort())
                .hasGateway(entity.getHasGateway())
                .copyArticulo(entity.getCopyArticulo())
                .build();
    }

    public NegocioEntity toEntity(Negocio domain) {
        if (domain == null) {
            return null;
        }
        return NegocioEntity.builder()
                .negocioId(domain.getNegocioId())
                .nombre(domain.getNombre())
                .negocioIdReal(domain.getNegocioIdReal())
                .databaseIpVpn(domain.getDatabaseIpVpn())
                .databaseIpLan(domain.getDatabaseIpLan())
                .databasePort(domain.getDatabasePort())
                .database(domain.getDatabase())
                .user(domain.getUser())
                .transferenciaStock(domain.getTransferenciaStock())
                .transferenciaValor(domain.getTransferenciaValor())
                .backendIpVpn(domain.getBackendIpVpn())
                .backendIpLan(domain.getBackendIpLan())
                .backendPort(domain.getBackendPort())
                .hasGateway(domain.getHasGateway())
                .copyArticulo(domain.getCopyArticulo())
                .build();
    }

}
