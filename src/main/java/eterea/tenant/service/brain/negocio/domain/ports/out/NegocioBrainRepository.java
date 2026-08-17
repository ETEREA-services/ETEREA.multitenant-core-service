package eterea.tenant.service.brain.negocio.domain.ports.out;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import java.util.List;
import java.util.Optional;

public interface NegocioBrainRepository {

    List<NegocioBrain> findAll();

    Optional<NegocioBrain> findById(Integer negocioId);

    Optional<NegocioBrain> findByTenantId(String tenantId);

    NegocioBrain save(NegocioBrain negocio);
}
