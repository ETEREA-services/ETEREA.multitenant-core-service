package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindCuentasUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindCuentasUseCaseImpl implements FindCuentasUseCase {

    private final CuentaRepository repository;

    public FindCuentasUseCaseImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cuenta> findAll() {
        return repository.findAllOrdered();
    }
}
