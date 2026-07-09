package eterea.tenant.service.tenant.hexagonal.articulomovimiento.application.usecases;

import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.ports.in.CreateArticuloMovimientoUseCase;
import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.ports.out.ArticuloMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateArticuloMovimientoUseCaseImpl implements CreateArticuloMovimientoUseCase {
    private final ArticuloMovimientoRepository repository;

    @Override
    public ArticuloMovimiento add(ArticuloMovimiento articuloMovimiento) {
        return repository.save(articuloMovimiento);
    }
}
