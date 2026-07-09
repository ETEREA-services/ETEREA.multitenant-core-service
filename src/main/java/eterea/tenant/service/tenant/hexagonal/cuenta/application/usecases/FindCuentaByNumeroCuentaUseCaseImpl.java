package eterea.tenant.service.tenant.hexagonal.cuenta.application.usecases;

import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in.FindCuentaByNumeroCuentaUseCase;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.tenant.kotlin.exception.CuentaException;
import org.springframework.stereotype.Component;

@Component
public class FindCuentaByNumeroCuentaUseCaseImpl implements FindCuentaByNumeroCuentaUseCase {

    private final CuentaRepository repository;

    public FindCuentaByNumeroCuentaUseCaseImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cuenta findByNumeroCuenta(Long numeroCuenta) {
        return repository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaException(numeroCuenta));
    }
}
