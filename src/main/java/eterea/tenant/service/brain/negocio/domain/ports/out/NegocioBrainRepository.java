package eterea.tenant.service.brain.negocio.domain.ports.out;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NegocioBrainRepository {

    List<NegocioBrain> findAll();

    List<NegocioBrain> findAllByEmpresaId(UUID empresaId);

    Optional<NegocioBrain> findById(Integer negocioId);

    Optional<NegocioBrain> findByTenantId(String tenantId);

    boolean existsByTenantId(String tenantId);

    NegocioBrain save(NegocioBrain negocio);
}
