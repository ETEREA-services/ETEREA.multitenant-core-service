package eterea.tenant.service.brain.negocio.domain.ports.in;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;

public interface CreateNegocioBrainUseCase {

    NegocioBrain create(NegocioBrain negocio);
}
