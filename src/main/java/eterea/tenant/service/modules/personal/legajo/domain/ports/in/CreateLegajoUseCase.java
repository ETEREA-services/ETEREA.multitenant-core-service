package eterea.tenant.service.modules.personal.legajo.domain.ports.in;

import eterea.tenant.service.modules.personal.legajo.domain.model.Legajo;

public interface CreateLegajoUseCase {

    Legajo createLegajo(Legajo legajo);

}
