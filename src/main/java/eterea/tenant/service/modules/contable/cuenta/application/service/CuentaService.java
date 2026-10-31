package eterea.tenant.service.modules.contable.cuenta.application.service;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.model.CuentaNodo;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.CreateCuentaUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.DeleteCuentaUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindAllCuentasUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindAllCuentasWithCuentaMaestroUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindCuentaArbolUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindCuentaByCuentaMaestroUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindCuentaByNumeroCuentaUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.FindCuentasUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.UpdateCuentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final FindAllCuentasUseCase findAllCuentasUseCase;
    private final FindAllCuentasWithCuentaMaestroUseCase findAllCuentasWithCuentaMaestroUseCase;
    private final FindCuentasUseCase findCuentasUseCase;
    private final FindCuentaByNumeroCuentaUseCase findCuentaByNumeroCuentaUseCase;
    private final FindCuentaByCuentaMaestroUseCase findCuentaByCuentaMaestroUseCase;
    private final FindCuentaArbolUseCase findCuentaArbolUseCase;
    private final CreateCuentaUseCase createCuentaUseCase;
    private final UpdateCuentaUseCase updateCuentaUseCase;
    private final DeleteCuentaUseCase deleteCuentaUseCase;

    public List<Cuenta> findAll() {
        return findAllCuentasUseCase.findAll();
    }

    public List<Cuenta> findAllOrdered() {
        return findCuentasUseCase.findAll();
    }

    public List<Cuenta> findAllWithCuentaMaestro() {
        return findAllCuentasWithCuentaMaestroUseCase.findAllWithCuentaMaestro();
    }

    public Cuenta findByNumeroCuenta(Long numeroCuenta) {
        return findCuentaByNumeroCuentaUseCase.findByNumeroCuenta(numeroCuenta);
    }

    public Cuenta findByCuentaMaestro(BigDecimal cuentaMaestro) {
        return findCuentaByCuentaMaestroUseCase.findByCuentaMaestro(cuentaMaestro);
    }

    public List<CuentaNodo> findArbol() {
        return findCuentaArbolUseCase.findArbol();
    }

    public Cuenta create(Cuenta cuenta) {
        return createCuentaUseCase.create(cuenta);
    }

    public Cuenta update(Long numeroCuenta, Cuenta cambios) {
        return updateCuentaUseCase.update(numeroCuenta, cambios);
    }

    public void delete(Long numeroCuenta) {
        deleteCuentaUseCase.delete(numeroCuenta);
    }
}
