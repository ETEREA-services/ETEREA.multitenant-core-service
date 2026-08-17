package eterea.tenant.service.brain.empresa.application.service;

import eterea.tenant.service.brain.empresa.application.exception.EmpresaBrainException;
import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.domain.ports.in.CreateEmpresaBrainUseCase;
import eterea.tenant.service.brain.empresa.domain.ports.in.GetAllEmpresaBrainsUseCase;
import eterea.tenant.service.brain.empresa.domain.ports.in.GetEmpresaBrainByIdUseCase;
import eterea.tenant.service.brain.empresa.domain.ports.in.UpdateEmpresaBrainUseCase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpresaBrainService {

    private final GetAllEmpresaBrainsUseCase getAllEmpresaBrainsUseCase;
    private final GetEmpresaBrainByIdUseCase getEmpresaBrainByIdUseCase;
    private final CreateEmpresaBrainUseCase createEmpresaBrainUseCase;
    private final UpdateEmpresaBrainUseCase updateEmpresaBrainUseCase;

    public List<EmpresaBrain> findAll() {
        return getAllEmpresaBrainsUseCase.getAll();
    }

    public EmpresaBrain findById(UUID empresaId) {
        return getEmpresaBrainByIdUseCase.getById(empresaId)
                .orElseThrow(() -> new EmpresaBrainException(empresaId));
    }

    public EmpresaBrain create(EmpresaBrain empresa) {
        return createEmpresaBrainUseCase.create(empresa);
    }

    public EmpresaBrain update(UUID empresaId, EmpresaBrain empresa) {
        return updateEmpresaBrainUseCase.update(empresaId, empresa)
                .orElseThrow(() -> new EmpresaBrainException(empresaId));
    }
}
