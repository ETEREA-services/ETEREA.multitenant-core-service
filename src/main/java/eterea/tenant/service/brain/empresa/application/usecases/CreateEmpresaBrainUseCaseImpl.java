package eterea.tenant.service.brain.empresa.application.usecases;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.domain.ports.in.CreateEmpresaBrainUseCase;
import eterea.tenant.service.brain.empresa.domain.ports.out.EmpresaBrainRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmpresaBrainUseCaseImpl implements CreateEmpresaBrainUseCase {

    private final EmpresaBrainRepository empresaBrainRepository;

    @Override
    public EmpresaBrain create(EmpresaBrain empresa) {
        empresa.setEmpresaId(UUID.randomUUID());
        return empresaBrainRepository.save(empresa);
    }
}
