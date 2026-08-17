package eterea.tenant.service.brain.negocio.application.usecases;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.in.CreateNegocioBrainUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateNegocioBrainUseCaseImpl implements CreateNegocioBrainUseCase {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    public NegocioBrain create(NegocioBrain negocio) {
        return negocioBrainRepository.save(negocio);
    }
}
