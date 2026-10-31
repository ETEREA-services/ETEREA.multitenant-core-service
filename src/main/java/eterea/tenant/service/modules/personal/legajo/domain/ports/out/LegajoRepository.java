package eterea.tenant.service.modules.personal.legajo.domain.ports.out;

import eterea.tenant.service.modules.personal.legajo.domain.model.Legajo;

import java.util.List;
import java.util.Optional;

public interface LegajoRepository {

    List<Legajo> findAll();
    Optional<Legajo> findByLegajoId(Integer legajoId);
    Legajo createLegajo(Legajo legajo);
    List<Legajo> findAllContainingSubstrings(List<String> substrings);

}
