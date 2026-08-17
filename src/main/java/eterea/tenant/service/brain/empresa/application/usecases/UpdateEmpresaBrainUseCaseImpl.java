package eterea.tenant.service.brain.empresa.application.usecases;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.domain.ports.in.UpdateEmpresaBrainUseCase;
import eterea.tenant.service.brain.empresa.domain.ports.out.EmpresaBrainRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateEmpresaBrainUseCaseImpl implements UpdateEmpresaBrainUseCase {

    private final EmpresaBrainRepository empresaBrainRepository;

    @Override
    public Optional<EmpresaBrain> update(UUID empresaId, EmpresaBrain empresa) {
        return empresaBrainRepository.findById(empresaId)
                .map(existing -> {
                    existing.setNombre(empresa.getNombre());
                    return empresaBrainRepository.save(existing);
                });
    }
}
