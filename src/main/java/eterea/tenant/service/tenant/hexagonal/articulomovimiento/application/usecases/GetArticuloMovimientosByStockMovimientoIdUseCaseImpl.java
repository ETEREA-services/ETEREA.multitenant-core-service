package eterea.tenant.service.tenant.hexagonal.articulomovimiento.application.usecases;

import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.ports.in.GetArticuloMovimientosByStockMovimientoIdUseCase;
import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.ports.out.ArticuloMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetArticuloMovimientosByStockMovimientoIdUseCaseImpl implements GetArticuloMovimientosByStockMovimientoIdUseCase {
    private final ArticuloMovimientoRepository repository;

    @Override
    public List<ArticuloMovimiento> findAllByStockMovimientoId(Long stockMovimientoId) {
        return repository.findAllByStockMovimientoId(stockMovimientoId);
    }
}
