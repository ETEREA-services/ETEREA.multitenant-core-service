package eterea.tenant.service.modules.stock.articulo.application.usecases;

import eterea.tenant.service.modules.stock.articulo.application.exception.ArticuloException;
import eterea.tenant.service.modules.stock.articulo.domain.model.Articulo;
import eterea.tenant.service.modules.stock.articulo.domain.ports.in.FindArticuloByArticuloIdUseCase;
import eterea.tenant.service.modules.stock.articulo.domain.ports.out.ArticuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindArticuloByArticuloIdUseCaseImpl implements FindArticuloByArticuloIdUseCase {

    private final ArticuloRepository articuloRepository;

    @Override
    public Articulo findByArticuloId(String articuloId) {
        return articuloRepository.findByArticuloId(articuloId)
                .orElseThrow(() -> new ArticuloException(articuloId));
    }
}
