package eterea.tenant.service.tenant.hexagonal.articulo.application.usecases;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in.CreateArticuloUseCase;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.out.ArticuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateArticuloUseCaseImpl implements CreateArticuloUseCase {

    private final ArticuloRepository articuloRepository;

    @Override
    public Articulo createArticulo(Articulo articulo) {
        articulo.setAutoNumericoId(null);
        return articuloRepository.save(articulo);
    }
}
