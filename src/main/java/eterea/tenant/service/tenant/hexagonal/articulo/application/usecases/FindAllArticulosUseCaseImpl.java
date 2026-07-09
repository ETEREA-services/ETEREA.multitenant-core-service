package eterea.tenant.service.tenant.hexagonal.articulo.application.usecases;

import eterea.tenant.service.tenant.hexagonal.articulo.domain.model.Articulo;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.in.FindAllArticulosUseCase;
import eterea.tenant.service.tenant.hexagonal.articulo.domain.ports.out.ArticuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllArticulosUseCaseImpl implements FindAllArticulosUseCase {

    private final ArticuloRepository articuloRepository;

    @Override
    public List<Articulo> findAll() {
        return articuloRepository.findAll();
    }
}
