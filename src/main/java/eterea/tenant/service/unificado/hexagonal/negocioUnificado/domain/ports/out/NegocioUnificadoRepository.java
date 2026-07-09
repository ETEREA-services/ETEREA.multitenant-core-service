package eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.out;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import java.util.List;
import java.util.Optional;

public interface NegocioUnificadoRepository {

    List<NegocioUnificado> findAll();

    Optional<NegocioUnificado> findByNegocioId(Integer negocioId);

    Optional<NegocioUnificado> findByTenantId(String tenantId);

}

