package eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;
import java.util.List;

public interface FindArticulosBySearchUseCase {
    List<Articulo> findBySearch(String chain);
}
