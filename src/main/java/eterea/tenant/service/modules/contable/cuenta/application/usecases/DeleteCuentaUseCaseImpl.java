package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CodigoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaValidator;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.DeleteCuentaUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaMovimientoPort;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteCuentaUseCaseImpl implements DeleteCuentaUseCase {

    private final CuentaRepository repository;
    private final CuentaMovimientoPort movimientoPort;

    public DeleteCuentaUseCaseImpl(
            CuentaRepository repository, CuentaMovimientoPort movimientoPort) {
        this.repository = repository;
        this.movimientoPort = movimientoPort;
    }

    @Override
    @Transactional
    public void delete(Long numeroCuenta) {
        CuentaValidator.validarNumeroCuenta(numeroCuenta);
        CodigoCuenta codigo = CodigoCuenta.of(numeroCuenta);
        repository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaInvalidaException(
                        "CUENTA_NO_EXISTE", "La cuenta " + numeroCuenta + " no existe."));

        if (repository.existsByNumeroCuentaGreaterThanAndNumeroCuentaLessThanEqual(
                numeroCuenta, codigo.limiteSuperior())) {
            throw new CuentaInvalidaException(
                    "CUENTA_CON_HIJOS",
                    "La cuenta tiene cuentas hijas; no se puede eliminar.");
        }

        if (movimientoPort.existeMovimientoEnRango(numeroCuenta, codigo.limiteSuperior())) {
            throw new CuentaInvalidaException(
                    "CUENTA_CON_MOVIMIENTOS",
                    "La cuenta tiene movimientos contables; no se puede eliminar.");
        }

        repository.deleteByNumeroCuenta(numeroCuenta);
    }
}
