package eterea.tenant.service.tenant.hexagonal.legajo.application.usecases;

import eterea.tenant.service.tenant.hexagonal.legajo.domain.model.Legajo;
import eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.in.GetLegajoByIdUseCase;
import eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.out.LegajoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetLegajoByIdUseCaseImpl implements GetLegajoByIdUseCase {

    private final LegajoRepository legajoRepository;

    @Override
    public Optional<Legajo> findByLegajoId(Integer legajoId) {
        return legajoRepository.findByLegajoId(legajoId);
    }

}
