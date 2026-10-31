package eterea.tenant.service.modules.stock.articulomovimiento.application.usecases;

import eterea.tenant.service.modules.stock.articulomovimiento.application.exception.ArticuloMovimientoException;
import eterea.tenant.service.modules.stock.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.modules.stock.articulomovimiento.domain.ports.in.GetArticuloMovimientoByIdUseCase;
import eterea.tenant.service.modules.stock.articulomovimiento.domain.ports.out.ArticuloMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetArticuloMovimientoByIdUseCaseImpl implements GetArticuloMovimientoByIdUseCase {

    private final ArticuloMovimientoRepository repository;

    @Override
    public ArticuloMovimiento findByArticuloMovimientoId(Long articuloMovimientoId) {
        return repository.findByArticuloMovimientoId(articuloMovimientoId)
                .orElseThrow(() -> new ArticuloMovimientoException(articuloMovimientoId));
    }
}
