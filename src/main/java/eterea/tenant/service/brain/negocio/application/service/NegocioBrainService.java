package eterea.tenant.service.brain.negocio.application.service;

import eterea.tenant.service.brain.negocio.application.exception.NegocioBrainException;
import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.in.CreateNegocioBrainUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.in.GetAllNegocioBrainsUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.in.GetNegocioBrainByIdUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.in.GetNegocioBrainByTenantIdUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.in.UpdateNegocioBrainUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NegocioBrainService {

    private final GetAllNegocioBrainsUseCase getAllNegocioBrainsUseCase;
    private final GetNegocioBrainByIdUseCase getNegocioBrainByIdUseCase;
    private final GetNegocioBrainByTenantIdUseCase getNegocioBrainByTenantIdUseCase;
    private final CreateNegocioBrainUseCase createNegocioBrainUseCase;
    private final UpdateNegocioBrainUseCase updateNegocioBrainUseCase;

    public List<NegocioBrain> findAll() {
        return getAllNegocioBrainsUseCase.getAll();
    }

    public NegocioBrain findById(Integer negocioId) {
        return getNegocioBrainByIdUseCase.getById(negocioId)
                .orElseThrow(() -> new NegocioBrainException(negocioId));
    }

    public NegocioBrain findByTenantId(String tenantId) {
        return getNegocioBrainByTenantIdUseCase.getByTenantId(tenantId)
                .orElseThrow(() -> new NegocioBrainException(tenantId));
    }

    public NegocioBrain create(NegocioBrain negocio) {
        return createNegocioBrainUseCase.create(negocio);
    }

    public NegocioBrain update(Integer negocioId, NegocioBrain negocio) {
        return updateNegocioBrainUseCase.update(negocioId, negocio)
                .orElseThrow(() -> new NegocioBrainException(negocioId));
    }
}
