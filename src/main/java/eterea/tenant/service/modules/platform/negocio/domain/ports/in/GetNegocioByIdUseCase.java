package eterea.tenant.service.modules.platform.negocio.domain.ports.in;

import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;

public interface GetNegocioByIdUseCase {

    Negocio getNegocioById(Integer negocioId);

}
