package eterea.tenant.service.tenant.hexagonal.cuenta.application.usecases;

import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in.FindCuentaByCuentaMaestroUseCase;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.tenant.kotlin.exception.CuentaException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FindCuentaByCuentaMaestroUseCaseImpl implements FindCuentaByCuentaMaestroUseCase {

    private final CuentaRepository repository;

    public FindCuentaByCuentaMaestroUseCaseImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cuenta findByCuentaMaestro(BigDecimal cuentaMaestro) {
        return repository.findByCuentaMaestro(cuentaMaestro)
                .orElseThrow(() -> new CuentaException(cuentaMaestro));
    }
}
