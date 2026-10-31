package eterea.tenant.service.modules.contable.cuenta.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaMovimientoPort;
import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaCuentaMovimientoAdapter implements CuentaMovimientoPort {

    private final CuentaMovimientoRepository repository;

    @Override
    public boolean existeMovimientoEnCuenta(Long numeroCuenta) {
        return repository.existsByNumeroCuenta(numeroCuenta);
    }

    @Override
    public boolean existeMovimientoEnRango(Long numeroCuentaDesde, Long numeroCuentaHasta) {
        return repository.existsByNumeroCuentaBetween(numeroCuentaDesde, numeroCuentaHasta);
    }
}
