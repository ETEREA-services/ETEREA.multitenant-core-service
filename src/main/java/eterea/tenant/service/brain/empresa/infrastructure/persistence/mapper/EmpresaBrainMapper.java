package eterea.tenant.service.brain.empresa.infrastructure.persistence.mapper;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.infrastructure.persistence.entity.EmpresaBrainEntity;
import org.springframework.stereotype.Component;

@Component
public class EmpresaBrainMapper {

    public EmpresaBrain toDomain(EmpresaBrainEntity entity) {
        if (entity == null) {
            return null;
        }
        return EmpresaBrain.builder()
                .empresaId(entity.getEmpresaId())
                .nombre(entity.getNombre())
                .build();
    }

    public EmpresaBrainEntity toEntity(EmpresaBrain domain) {
        if (domain == null) {
            return null;
        }
        return EmpresaBrainEntity.builder()
                .empresaId(domain.getEmpresaId())
                .nombre(domain.getNombre())
                .build();
    }
}
