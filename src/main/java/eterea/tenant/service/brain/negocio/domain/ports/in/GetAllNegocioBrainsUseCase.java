package eterea.tenant.service.brain.negocio.domain.ports.in;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import java.util.List;

public interface GetAllNegocioBrainsUseCase {

    List<NegocioBrain> getAll();
}
