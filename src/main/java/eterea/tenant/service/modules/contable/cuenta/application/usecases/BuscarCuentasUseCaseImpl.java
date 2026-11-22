package eterea.tenant.service.modules.contable.cuenta.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.in.BuscarCuentasUseCase;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.NegocioActualPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Resuelve el negocio activo y delega la búsqueda legacy de cuentas.
 *
 * <p>La consulta es por nombre, sólo imputables ({@code Integra = 0}), ordenada
 * por nombre y limitada a 20 resultados. No busca por código: replica el
 * comportamiento de {@code clsDAOPlanCta.coleccionBuscar}.</p>
 */
@Component
@RequiredArgsConstructor
public class BuscarCuentasUseCaseImpl implements BuscarCuentasUseCase {

    private final CuentaRepository cuentaRepository;
    private final NegocioActualPort negocioActualPort;

    @Override
    public List<Cuenta> buscar(String texto) {
        int negocioId = negocioActualPort.findNegocioId()
                .orElseThrow(() -> new CuentaInvalidaException(
                        "CUENTA_SIN_NEGOCIO",
                        "No hay un negocio activo para buscar cuentas."));
        String filtro = texto == null ? "" : texto.trim();
        return cuentaRepository.buscarImputables(negocioId, filtro);
    }

}
