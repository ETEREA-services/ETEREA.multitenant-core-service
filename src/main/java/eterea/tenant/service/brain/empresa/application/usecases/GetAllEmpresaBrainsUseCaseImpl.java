package eterea.tenant.service.brain.empresa.application.usecases;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.domain.ports.in.GetAllEmpresaBrainsUseCase;
import eterea.tenant.service.brain.empresa.domain.ports.out.EmpresaBrainRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllEmpresaBrainsUseCaseImpl implements GetAllEmpresaBrainsUseCase {

    private final EmpresaBrainRepository empresaBrainRepository;

    @Override
    public List<EmpresaBrain> getAll() {
        return empresaBrainRepository.findAll();
    }
}
