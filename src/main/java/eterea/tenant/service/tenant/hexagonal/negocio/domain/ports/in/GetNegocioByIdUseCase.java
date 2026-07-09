package eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;

public interface GetNegocioByIdUseCase {

    Negocio getNegocioById(Integer negocioId);

}
