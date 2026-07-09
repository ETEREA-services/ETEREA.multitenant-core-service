package eterea.tenant.service.tenant.hexagonal.legajo.application.usecases;

import eterea.tenant.service.tenant.hexagonal.legajo.domain.model.Legajo;
import eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.in.CreateLegajoUseCase;
import eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.out.LegajoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateLegajoUseCaseImpl implements CreateLegajoUseCase {

    private final LegajoRepository legajoRepository;

    @Override
    public Legajo createLegajo(Legajo legajo) {
        return legajoRepository.createLegajo(legajo);
    }

}
