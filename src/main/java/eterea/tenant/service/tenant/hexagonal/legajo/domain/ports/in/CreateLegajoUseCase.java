package eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.legajo.domain.model.Legajo;

public interface CreateLegajoUseCase {

    Legajo createLegajo(Legajo legajo);

}
