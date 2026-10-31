package eterea.tenant.service.modules.stock.articulo.domain.ports.in;

import eterea.tenant.service.modules.stock.articulo.domain.model.Articulo;

public interface UpdateArticuloUseCase {
    Articulo updateArticulo(String articuloId, Articulo articulo);
}
