package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaValidator;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindCuentaByNumeroCuentaUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.springframework.stereotype.Component;

@Component
public class FindCuentaByNumeroCuentaUseCaseImpl implements FindCuentaByNumeroCuentaUseCase {

    private final CuentaRepository repository;

    public FindCuentaByNumeroCuentaUseCaseImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cuenta findByNumeroCuenta(Long numeroCuenta) {
        CuentaValidator.validarNumeroCuenta(numeroCuenta);
        return repository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaInvalidaException(
                        "CUENTA_NO_EXISTE", "La cuenta " + numeroCuenta + " no existe."));
    }
}
