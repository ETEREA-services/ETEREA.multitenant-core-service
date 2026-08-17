package eterea.tenant.service.brain.negocio.application.usecases;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.in.GetAllNegocioBrainsUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllNegocioBrainsUseCaseImpl implements GetAllNegocioBrainsUseCase {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    public List<NegocioBrain> getAll() {
        return negocioBrainRepository.findAll();
    }
}
