package eterea.tenant.service.modules.contable.coeficienteinflacion.application.usecases;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionValidator;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.in.FindCoeficientesAnioUseCase;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.out.CoeficienteInflacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindCoeficientesAnioUseCaseImpl implements FindCoeficientesAnioUseCase {

    private final CoeficienteInflacionRepository repository;

    @Override
    public CoeficienteInflacionAnio find(int anho) {
        CoeficienteInflacionValidator.validarAnio(anho);
        return CoeficienteInflacionAnio.of(anho, repository.findAllByAnho(anho));
    }

}
