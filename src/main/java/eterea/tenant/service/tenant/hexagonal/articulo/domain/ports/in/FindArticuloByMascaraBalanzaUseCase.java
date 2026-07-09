package eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;

public interface FindArticuloByMascaraBalanzaUseCase {
    Articulo findByMascaraBalanza(String mascaraBalanza);
}
