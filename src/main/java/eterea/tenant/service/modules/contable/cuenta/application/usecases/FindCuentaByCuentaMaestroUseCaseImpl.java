package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindCuentaByCuentaMaestroUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.legacy.CuentaException;
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
