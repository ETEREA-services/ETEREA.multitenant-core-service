package eterea.tenant.service.brain.negocio.application.usecases;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.in.GetNegocioBrainByTenantIdUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetNegocioBrainByTenantIdUseCaseImpl implements GetNegocioBrainByTenantIdUseCase {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    public Optional<NegocioBrain> getByTenantId(String tenantId) {
        return negocioBrainRepository.findByTenantId(tenantId);
    }
}
