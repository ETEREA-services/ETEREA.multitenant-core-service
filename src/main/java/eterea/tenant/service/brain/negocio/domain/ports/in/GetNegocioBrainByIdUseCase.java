package eterea.tenant.service.brain.negocio.domain.ports.in;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import java.util.Optional;

public interface GetNegocioBrainByIdUseCase {

    Optional<NegocioBrain> getById(Integer negocioId);
}
