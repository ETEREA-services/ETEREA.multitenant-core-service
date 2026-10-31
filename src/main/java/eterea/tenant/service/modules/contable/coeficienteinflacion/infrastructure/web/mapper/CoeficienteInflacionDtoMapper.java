package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.mapper;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteMensual;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteMensualCambio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.dto.CoeficienteInflacionAnioResponse;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.dto.CoeficienteInflacionAnioUpdateRequest;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.dto.CoeficienteMensualResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoeficienteInflacionDtoMapper {

    public List<CoeficienteMensualCambio> toCambios(
            CoeficienteInflacionAnioUpdateRequest request) {
        if (request == null || request.getMeses() == null) {
            return List.of();
        }
        return request.getMeses().stream()
                .map(mes -> new CoeficienteMensualCambio(mes.getMes(), mes.getCoeficiente()))
                .toList();
    }

    public CoeficienteInflacionAnioResponse toResponse(CoeficienteInflacionAnio anio) {
        if (anio == null) {
            return null;
        }
        return CoeficienteInflacionAnioResponse.builder()
                .anho(anio.getAnho())
                .meses(anio.getMeses().stream().map(this::toResponse).toList())
                .cargados(anio.cargados())
                .total(anio.total())
                .build();
    }

    public CoeficienteMensualResponse toResponse(CoeficienteMensual mensual) {
        if (mensual == null) {
            return null;
        }
        return CoeficienteMensualResponse.builder()
                .mes(mensual.mes())
                .coeficiente(mensual.coeficiente())
                .cargado(mensual.cargado())
                .coeficienteInflacionId(mensual.coeficienteInflacionId())
                .created(mensual.created())
                .build();
    }

}
