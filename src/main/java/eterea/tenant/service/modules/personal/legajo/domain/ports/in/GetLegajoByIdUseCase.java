package eterea.tenant.service.modules.personal.legajo.domain.ports.in;

import eterea.tenant.service.modules.personal.legajo.domain.model.Legajo;

import java.util.Optional;

public interface GetLegajoByIdUseCase {

    Optional<Legajo> findByLegajoId(Integer legajoId);

}
