package eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.legajo.domain.model.Legajo;

import java.util.Optional;

public interface GetLegajoByIdUseCase {

    Optional<Legajo> findByLegajoId(Integer legajoId);

}
