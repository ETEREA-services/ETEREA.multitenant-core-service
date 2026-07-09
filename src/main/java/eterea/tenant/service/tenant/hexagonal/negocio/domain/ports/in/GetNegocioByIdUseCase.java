package eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.in;


import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;

import java.util.Optional;

public interface GetNegocioByIdUseCase {

    Optional<Negocio> getNegocioById(Integer negocioId);

}
