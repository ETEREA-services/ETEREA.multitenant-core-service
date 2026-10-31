package eterea.tenant.service.modules.stock.articulo.domain.ports.in;

import eterea.tenant.service.modules.stock.articulo.domain.model.Articulo;
import java.util.List;

public interface FindArticulosBySearchUseCase {
    List<Articulo> findBySearch(String chain);
}
