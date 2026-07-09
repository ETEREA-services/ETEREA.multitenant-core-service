package eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.out;


import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;

import java.util.List;
import java.util.Optional;

public interface NegocioRepository {

    List<Negocio> findAllByCopyArticulo(Byte copyArticulo);
    List<Negocio> findAllWithIpAddress(Integer negocioId);
    Optional<Negocio> findByNegocioId(Integer negocioId);

}
