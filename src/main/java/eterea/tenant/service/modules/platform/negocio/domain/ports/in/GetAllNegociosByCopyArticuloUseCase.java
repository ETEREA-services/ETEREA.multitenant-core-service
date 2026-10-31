package eterea.tenant.service.modules.platform.negocio.domain.ports.in;


import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;

import java.util.List;

public interface GetAllNegociosByCopyArticuloUseCase {

    List<Negocio> getAllNegociosByCopyArticulo(Byte copyArticulo);

}
