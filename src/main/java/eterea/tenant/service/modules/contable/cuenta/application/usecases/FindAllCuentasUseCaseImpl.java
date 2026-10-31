package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindAllCuentasUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllCuentasUseCaseImpl implements FindAllCuentasUseCase {

    private final CuentaRepository repository;

    public FindAllCuentasUseCaseImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cuenta> findAll() {
        return repository.findAll();
    }
}
