package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.mapper;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacion;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.entity.CoeficienteInflacionEntity;
import org.springframework.stereotype.Component;

@Component
public class CoeficienteInflacionMapper {

    public CoeficienteInflacion toDomain(CoeficienteInflacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return CoeficienteInflacion.builder()
                .coeficienteInflacionId(entity.getCoeficienteInflacionId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .coeficiente(entity.getCoeficiente())
                .created(entity.getCreated())
                .build();
    }

    public CoeficienteInflacionEntity toEntity(CoeficienteInflacion domain) {
        if (domain == null) {
            return null;
        }
        return CoeficienteInflacionEntity.builder()
                .coeficienteInflacionId(domain.getCoeficienteInflacionId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .coeficiente(domain.getCoeficiente())
                .created(domain.getCreated())
                .build();
    }

}
