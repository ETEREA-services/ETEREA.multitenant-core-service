package eterea.tenant.service.brain.empresa.application.usecases;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.domain.ports.in.GetEmpresaBrainByIdUseCase;
import eterea.tenant.service.brain.empresa.domain.ports.out.EmpresaBrainRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetEmpresaBrainByIdUseCaseImpl implements GetEmpresaBrainByIdUseCase {

    private final EmpresaBrainRepository empresaBrainRepository;

    @Override
    public Optional<EmpresaBrain> getById(UUID empresaId) {
        return empresaBrainRepository.findById(empresaId);
    }
}
