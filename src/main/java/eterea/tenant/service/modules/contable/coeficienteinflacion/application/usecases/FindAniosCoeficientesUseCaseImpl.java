package eterea.tenant.service.modules.contable.coeficienteinflacion.application.usecases;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.in.FindAniosCoeficientesUseCase;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.out.CoeficienteInflacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAniosCoeficientesUseCaseImpl implements FindAniosCoeficientesUseCase {

    private final CoeficienteInflacionRepository repository;

    @Override
    public List<Integer> findAll() {
        return repository.findAnios();
    }

}
