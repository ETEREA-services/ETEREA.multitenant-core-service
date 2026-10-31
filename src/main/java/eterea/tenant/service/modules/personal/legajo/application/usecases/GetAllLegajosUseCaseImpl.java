package eterea.tenant.service.modules.personal.legajo.application.usecases;

import eterea.tenant.service.modules.personal.legajo.domain.model.Legajo;
import eterea.tenant.service.modules.personal.legajo.domain.ports.in.GetAllLegajosUseCase;
import eterea.tenant.service.modules.personal.legajo.domain.ports.out.LegajoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllLegajosUseCaseImpl implements GetAllLegajosUseCase {

    private final LegajoRepository legajoRepository;

    @Override
    public List<Legajo> getAllLegajos() {
        return legajoRepository.findAll();
    }

}
