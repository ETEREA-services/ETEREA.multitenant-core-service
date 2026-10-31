package eterea.tenant.service.modules.platform.negocio.domain.ports.out;


import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;

import java.util.List;
import java.util.Optional;

public interface NegocioRepository {

    List<Negocio> findAllByCopyArticulo(Byte copyArticulo);
    List<Negocio> findAllWithIpAddress(Integer negocioId);
    Optional<Negocio> findByNegocioId(Integer negocioId);
    Optional<Negocio> findByDatabase(String database);

}
