package eterea.tenant.service.brain.empresa.domain.ports.out;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmpresaBrainRepository {

    List<EmpresaBrain> findAll();

    Optional<EmpresaBrain> findById(UUID empresaId);

    EmpresaBrain save(EmpresaBrain empresa);
}
