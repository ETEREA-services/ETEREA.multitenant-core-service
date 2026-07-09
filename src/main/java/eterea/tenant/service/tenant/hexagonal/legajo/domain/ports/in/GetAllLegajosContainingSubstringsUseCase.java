package eterea.tenant.service.tenant.hexagonal.legajo.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.legajo.domain.model.Legajo;

import java.util.List;

public interface GetAllLegajosContainingSubstringsUseCase {

    List<Legajo> findAllContainingSubstrings(List<String> substrings);

}
