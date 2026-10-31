package eterea.tenant.service.brain.negocio.domain.ports.in;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import java.util.List;
import java.util.UUID;

public interface GetNegocioBrainsByEmpresaIdUseCase {

    List<NegocioBrain> getByEmpresaId(UUID empresaId);
}
