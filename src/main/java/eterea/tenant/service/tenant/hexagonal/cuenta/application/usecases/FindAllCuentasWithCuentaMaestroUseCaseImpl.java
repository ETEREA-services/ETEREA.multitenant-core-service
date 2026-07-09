package eterea.tenant.service.tenant.hexagonal.cuenta.application.usecases;

import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in.FindAllCuentasWithCuentaMaestroUseCase;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.out.CuentaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class FindAllCuentasWithCuentaMaestroUseCaseImpl implements FindAllCuentasWithCuentaMaestroUseCase {

    private final CuentaRepository repository;

    public FindAllCuentasWithCuentaMaestroUseCaseImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cuenta> findAllWithCuentaMaestro() {
        return repository.findAllByCuentaMaestroGreaterThan(BigDecimal.ZERO);
    }
}
