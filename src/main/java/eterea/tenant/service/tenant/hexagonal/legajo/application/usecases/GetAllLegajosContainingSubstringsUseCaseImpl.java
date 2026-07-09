package eterea.tenant.service.tenant.hexagonal.legajo.application.usecases;

import eterea.tenant.service.tenant.hexagonal.legajo.domain.model.Legajo;
import eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.in.GetAllLegajosContainingSubstringsUseCase;
import eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.out.LegajoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllLegajosContainingSubstringsUseCaseImpl implements GetAllLegajosContainingSubstringsUseCase {

    private final LegajoRepository legajoRepository;

    @Override
    public List<Legajo> findAllContainingSubstrings(List<String> substrings) {
        return legajoRepository.findAllContainingSubstrings(substrings);
    }

}
