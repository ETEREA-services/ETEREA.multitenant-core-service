package eterea.tenant.service.modules.contable.cuenta.infrastructure.api;

import eterea.tenant.service.modules.contable.api.CuentaQuery;
import eterea.tenant.service.modules.contable.api.CuentaResumen;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementación del contrato {@link CuentaQuery}.
 *
 * <p>Usa el repositorio (no el caso de uso) para devolver vacío cuando la
 * cuenta no existe, sin romper los endpoints que solo muestran la cuenta
 * asociada.</p>
 */
@Component
@RequiredArgsConstructor
public class CuentaQueryAdapter implements CuentaQuery {

    private final CuentaRepository cuentaRepository;

    @Override
    public Optional<CuentaResumen> findByNumeroCuenta(Long numeroCuenta) {
        if (numeroCuenta == null) {
            return Optional.empty();
        }
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .map(CuentaQueryAdapter::toResumen);
    }

    private static CuentaResumen toResumen(Cuenta cuenta) {
        return new CuentaResumen(
                cuenta.getNumeroCuenta(),
                cuenta.getNombre(),
                cuenta.getNegocioId(),
                cuenta.getIntegra(),
                cuenta.getTransfiere(),
                cuenta.getMovimientoStock(),
                cuenta.getCuentaMaestro(),
                cuenta.getGrado(),
                cuenta.getGrado1(),
                cuenta.getGrado2(),
                cuenta.getGrado3(),
                cuenta.getGrado4(),
                cuenta.getVentas(),
                cuenta.getCompras(),
                cuenta.getGastos());
    }

}
