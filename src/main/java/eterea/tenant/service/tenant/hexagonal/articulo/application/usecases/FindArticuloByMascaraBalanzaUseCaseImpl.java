package eterea.tenant.service.tenant.hexagonal.articulo.application.usecases;

import eterea.tenant.service.tenant.hexagonal.articulo.application.exception.ArticuloException;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in.FindArticuloByMascaraBalanzaUseCase;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.out.ArticuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindArticuloByMascaraBalanzaUseCaseImpl implements FindArticuloByMascaraBalanzaUseCase {

    private final ArticuloRepository articuloRepository;

    @Override
    public Articulo findByMascaraBalanza(String mascaraBalanza) {
        return articuloRepository.findByMascaraBalanza(mascaraBalanza)
                .orElseThrow(() -> new ArticuloException(mascaraBalanza));
    }
}
