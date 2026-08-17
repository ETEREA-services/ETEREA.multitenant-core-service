package eterea.tenant.service.brain.empresa.domain.ports.in;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import java.util.List;

public interface GetAllEmpresaBrainsUseCase {

    List<EmpresaBrain> getAll();
}
