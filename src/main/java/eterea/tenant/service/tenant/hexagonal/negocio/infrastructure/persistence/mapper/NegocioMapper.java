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
        return new Negocio(
                entity.getNegocioId(),
                entity.getNombre(),
                entity.getNegocioIdReal(),
                entity.getDatabaseIpVpn(),
                entity.getDatabaseIpLan(),
                entity.getDatabasePort(),
                entity.getDatabase(),
                entity.getUser(),
                entity.getTransferenciaStock(),
                entity.getTransferenciaValor(),
                entity.getBackendIpVpn(),
                entity.getBackendIpLan(),
                entity.getBackendPort(),
                entity.getHasGateway(),
                entity.getCopyArticulo(),
                null,
                null
        );
    }

}
