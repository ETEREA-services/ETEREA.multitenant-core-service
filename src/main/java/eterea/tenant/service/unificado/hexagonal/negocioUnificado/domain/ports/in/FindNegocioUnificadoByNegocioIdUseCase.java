package eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.in;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;

public interface FindNegocioUnificadoByNegocioIdUseCase {
    NegocioUnificado findByNegocioId(Integer negocioId);
}
