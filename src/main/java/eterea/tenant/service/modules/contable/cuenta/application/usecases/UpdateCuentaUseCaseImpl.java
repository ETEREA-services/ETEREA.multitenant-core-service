package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CodigoCuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaValidator;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.UpdateCuentaUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class UpdateCuentaUseCaseImpl implements UpdateCuentaUseCase {

    private final CuentaRepository repository;

    public UpdateCuentaUseCaseImpl(CuentaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Cuenta update(Long numeroCuenta, Cuenta cambios) {
        CuentaValidator.validarNumeroCuenta(numeroCuenta);
        CodigoCuenta.of(numeroCuenta);
        Cuenta existente = repository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaInvalidaException(
                        "CUENTA_NO_EXISTE", "La cuenta " + numeroCuenta + " no existe."));

        if (cambios.getNumeroCuenta() != null
                && !cambios.getNumeroCuenta().equals(numeroCuenta)) {
            throw new CuentaInvalidaException(
                    "CUENTA_CODIGO_INMUTABLE", "El código de la cuenta no se puede cambiar.");
        }

        CuentaValidator.validarNombre(cambios.getNombre());
        CuentaValidator.validarCuentaMaestro(cambios.getCuentaMaestro());

        existente.setNombre(cambios.getNombre().trim());
        existente.setCuentaMaestro(cambios.getCuentaMaestro() == null
                ? BigDecimal.ZERO : cambios.getCuentaMaestro());
        existente.setTransfiere(CuentaValidator.flag(cambios.getTransfiere()));
        existente.setMovimientoStock(CuentaValidator.flag(cambios.getMovimientoStock()));
        existente.setVentas(CuentaValidator.flag(cambios.getVentas()));
        existente.setCompras(CuentaValidator.flag(cambios.getCompras()));
        existente.setGastos(CuentaValidator.flag(cambios.getGastos()));

        return repository.save(existente);
    }
}
