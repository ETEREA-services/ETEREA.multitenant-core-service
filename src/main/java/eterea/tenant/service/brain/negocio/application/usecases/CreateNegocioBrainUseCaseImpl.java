package eterea.tenant.service.brain.negocio.application.usecases;

import eterea.tenant.service.brain.negocio.application.exception.NegocioBrainConflictException;
import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.in.CreateNegocioBrainUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateNegocioBrainUseCaseImpl implements CreateNegocioBrainUseCase {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    @Transactional
    public NegocioBrain create(NegocioBrain negocio) {
        if (negocio.getTenantId() == null || negocio.getTenantId().isBlank()) {
            throw new IllegalArgumentException("El tenantId es obligatorio");
        }
        if (negocioBrainRepository.existsByTenantId(negocio.getTenantId())) {
            throw new NegocioBrainConflictException(
                    "Ya existe un negocio con el tenantId: " + negocio.getTenantId());
        }
        negocio.setNegocioId(null);
        return negocioBrainRepository.save(negocio);
    }
}
