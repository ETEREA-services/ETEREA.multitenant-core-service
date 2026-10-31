package eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.mapper;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.entity.EjercicioEntity;
import org.springframework.stereotype.Component;

@Component
public class EjercicioMapper {

    public Ejercicio toDomain(EjercicioEntity entity) {
        if (entity == null) {
            return null;
        }
        return Ejercicio.builder()
                .ejercicioId(entity.getEjercicioId())
                .nombre(entity.getNombre())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .bloqueado(entity.isBloqueado())
                .created(entity.getCreated())
                .build();
    }

    public EjercicioEntity toEntity(Ejercicio domain) {
        if (domain == null) {
            return null;
        }
        return EjercicioEntity.builder()
                .ejercicioId(domain.getEjercicioId())
                .nombre(domain.getNombre())
                .fechaInicio(domain.getFechaInicio())
                .fechaFin(domain.getFechaFin())
                .bloqueado(domain.isBloqueado())
                .created(domain.getCreated())
                .build();
    }

}
