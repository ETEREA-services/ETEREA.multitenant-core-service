package eterea.tenant.service.tenant.hexagonal.articulomovimiento.application.usecases;

import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.model.ArticuloMovimiento;
import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.ports.in.GetArticuloMovimientosByClienteMovimientoIdUseCase;
import eterea.tenant.service.tenant.hexagonal.articulomovimiento.domain.ports.out.ArticuloMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetArticuloMovimientosByClienteMovimientoIdUseCaseImpl implements GetArticuloMovimientosByClienteMovimientoIdUseCase {
    private final ArticuloMovimientoRepository repository;

    @Override
    public List<ArticuloMovimiento> findAllByClienteMovimientoId(Long clienteMovimientoId) {
        return repository.findAllByClienteMovimientoId(clienteMovimientoId);
    }
}
