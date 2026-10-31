package eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.mapper;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioSugerido;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EstadoEjercicio;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.EjercicioRequest;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.EjercicioResponse;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.EjercicioUpdateRequest;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.dto.SiguienteEjercicioResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EjercicioDtoMapper {

    public Ejercicio toDomain(EjercicioRequest request) {
        return Ejercicio.builder()
                .nombre(request.getNombre())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .build();
    }

    public Ejercicio toDomain(EjercicioUpdateRequest request) {
        return Ejercicio.builder()
                .nombre(request.getNombre())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .build();
    }

    public EjercicioResponse toResponse(Ejercicio ejercicio) {
        if (ejercicio == null) {
            return null;
        }
        return EjercicioResponse.builder()
                .ejercicioId(ejercicio.getEjercicioId())
                .nombre(ejercicio.getNombre())
                .fechaInicio(ejercicio.getFechaInicio())
                .fechaFin(ejercicio.getFechaFin())
                .bloqueado(ejercicio.isBloqueado())
                .estado(EstadoEjercicio.de(
                        ejercicio.getFechaInicio(), ejercicio.getFechaFin(), LocalDate.now()))
                .created(ejercicio.getCreated())
                .build();
    }

    public SiguienteEjercicioResponse toResponse(EjercicioSugerido sugerido) {
        if (sugerido == null) {
            return null;
        }
        return SiguienteEjercicioResponse.builder()
                .disponible(sugerido.disponible())
                .nombre(sugerido.nombre())
                .fechaInicio(sugerido.fechaInicio())
                .fechaFin(sugerido.fechaFin())
                .origen(sugerido.origen())
                .motivo(sugerido.motivo())
                .build();
    }

}
