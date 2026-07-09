package eterea.tenant.service.tenant.hexagonal.articulo.application.usecases;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in.FindArticulosBySearchUseCase;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.out.ArticuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindArticulosBySearchUseCaseImpl implements FindArticulosBySearchUseCase {

    private final ArticuloRepository articuloRepository;

    @Override
    public List<Articulo> findBySearch(String chain) {
        return articuloRepository.findByDescripcionLike("%" + chain + "%");
    }
}
