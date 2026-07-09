package eterea.tenant.service.tenant.hexagonal.cuenta.application.service;

import eterea.tenant.service.tenant.hexagonal.cuenta.domain.model.Cuenta;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in.*;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in.FindAllCuentasUseCase;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in.FindAllCuentasWithCuentaMaestroUseCase;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in.FindCuentaByCuentaMaestroUseCase;
import eterea.tenant.service.tenant.hexagonal.cuenta.domain.ports.in.FindCuentaByNumeroCuentaUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {

    private final FindAllCuentasUseCase findAllCuentasUseCase;
    private final FindAllCuentasWithCuentaMaestroUseCase findAllCuentasWithCuentaMaestroUseCase;
    private final FindCuentaByNumeroCuentaUseCase findCuentaByNumeroCuentaUseCase;
    private final FindCuentaByCuentaMaestroUseCase findCuentaByCuentaMaestroUseCase;

    public CuentaService(FindAllCuentasUseCase findAllCuentasUseCase,
                         FindAllCuentasWithCuentaMaestroUseCase findAllCuentasWithCuentaMaestroUseCase,
                         FindCuentaByNumeroCuentaUseCase findCuentaByNumeroCuentaUseCase,
                         FindCuentaByCuentaMaestroUseCase findCuentaByCuentaMaestroUseCase) {
        this.findAllCuentasUseCase = findAllCuentasUseCase;
        this.findAllCuentasWithCuentaMaestroUseCase = findAllCuentasWithCuentaMaestroUseCase;
        this.findCuentaByNumeroCuentaUseCase = findCuentaByNumeroCuentaUseCase;
        this.findCuentaByCuentaMaestroUseCase = findCuentaByCuentaMaestroUseCase;
    }

    public List<Cuenta> findAll() {
        return findAllCuentasUseCase.findAll();
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

}
