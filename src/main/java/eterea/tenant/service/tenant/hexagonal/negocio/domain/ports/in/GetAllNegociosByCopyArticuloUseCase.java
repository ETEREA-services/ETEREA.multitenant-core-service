package eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.in;


import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;

import java.util.List;

public interface GetAllNegociosByCopyArticuloUseCase {

    List<Negocio> getAllNegociosByCopyArticulo(Byte copyArticulo);

}
