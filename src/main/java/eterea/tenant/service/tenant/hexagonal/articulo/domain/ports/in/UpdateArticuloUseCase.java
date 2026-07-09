package eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;

public interface UpdateArticuloUseCase {
    Articulo updateArticulo(String articuloId, Articulo articulo);
}
