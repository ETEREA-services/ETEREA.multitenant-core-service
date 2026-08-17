package eterea.tenant.service.brain.empresa.domain.ports.in;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import java.util.Optional;
import java.util.UUID;

public interface UpdateEmpresaBrainUseCase {

    Optional<EmpresaBrain> update(UUID empresaId, EmpresaBrain empresa);
}
