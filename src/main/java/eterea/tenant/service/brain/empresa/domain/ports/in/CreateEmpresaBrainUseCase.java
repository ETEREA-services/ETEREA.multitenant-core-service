package eterea.tenant.service.brain.empresa.domain.ports.in;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;

public interface CreateEmpresaBrainUseCase {

    EmpresaBrain create(EmpresaBrain empresa);
}
