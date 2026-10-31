package eterea.tenant.service.brain.negocio.application.usecases;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.in.GetNegocioBrainsByEmpresaIdUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetNegocioBrainsByEmpresaIdUseCaseImpl implements GetNegocioBrainsByEmpresaIdUseCase {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    public List<NegocioBrain> getByEmpresaId(UUID empresaId) {
        return negocioBrainRepository.findAllByEmpresaId(empresaId);
    }
}
