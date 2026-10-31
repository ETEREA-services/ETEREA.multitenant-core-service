package eterea.tenant.service.modules.personal.legajo.domain.ports.in;

import eterea.tenant.service.modules.personal.legajo.domain.model.Legajo;

import java.util.List;

public interface GetAllLegajosUseCase {

    List<Legajo> getAllLegajos();

}
