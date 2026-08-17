package eterea.tenant.service.brain.negocio.application.usecases;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.in.GetNegocioBrainByIdUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetNegocioBrainByIdUseCaseImpl implements GetNegocioBrainByIdUseCase {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    public Optional<NegocioBrain> getById(Integer negocioId) {
        return negocioBrainRepository.findById(negocioId);
    }
}
