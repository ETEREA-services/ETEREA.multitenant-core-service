package eterea.tenant.service.modules.contable.coeficienteinflacion.application.usecases;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacion;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionValidator;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteMensualCambio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.in.SaveCoeficientesAnioUseCase;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.out.CoeficienteInflacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SaveCoeficientesAnioUseCaseImpl implements SaveCoeficientesAnioUseCase {

    private final CoeficienteInflacionRepository repository;

    @Override
    @Transactional
    public CoeficienteInflacionAnio save(int anho, List<CoeficienteMensualCambio> cambios) {
        CoeficienteInflacionValidator.validarAnio(anho);
        CoeficienteInflacionValidator.validarCambios(cambios);

        for (CoeficienteMensualCambio cambio : cambios) {
            Optional<CoeficienteInflacion> existente =
                    repository.findByAnhoAndMes(anho, cambio.mes());

            if (cambio.coeficiente() == null) {
                existente.ifPresent(registro ->
                        repository.deleteById(registro.getCoeficienteInflacionId()));
                continue;
            }

            BigDecimal valor = CoeficienteInflacionValidator.normalizar(cambio.coeficiente());
            if (existente.isPresent()) {
                CoeficienteInflacion registro = existente.get();
                if (registro.getCoeficiente() == null
                        || registro.getCoeficiente().compareTo(valor) != 0) {
                    registro.setCoeficiente(valor);
                    repository.save(registro);
                }
            } else {
                repository.save(CoeficienteInflacion.builder()
                        .anho(anho)
                        .mes(cambio.mes())
                        .coeficiente(valor)
                        .build());
            }
        }

        return CoeficienteInflacionAnio.of(anho, repository.findAllByAnho(anho));
    }

}
