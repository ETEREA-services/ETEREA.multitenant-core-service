package eterea.tenant.service.modules.contable.balance.application.usecases;

import eterea.tenant.service.modules.contable.balance.application.service.BalanceCache;
import eterea.tenant.service.modules.contable.balance.application.service.BalanceCalculator;
import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;
import eterea.tenant.service.modules.contable.balance.domain.model.Balance;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.SaldoCuenta;
import eterea.tenant.service.modules.contable.balance.domain.ports.in.GenerarBalanceUseCase;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.BalanceMovimientoPort;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.CotizacionOnDemandPort;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Orquesta la generación del balance: resuelve el negocio, valida la cobertura
 * de cotizaciones, lee el plan y los agregados, y delega el cálculo.
 */
@Component
@RequiredArgsConstructor
public class GenerarBalanceUseCaseImpl implements GenerarBalanceUseCase {

    private final CuentaRepository cuentaRepository;
    private final BalanceMovimientoPort balanceMovimientoPort;
    private final CotizacionOnDemandPort cotizacionOnDemand;
    private final BalanceCalculator balanceCalculator;
    private final BalanceCache balanceCache;
    private final NegocioActual negocioActual;

    @Override
    public Balance generar(BalanceFiltro filtro) {
        int negocioId = negocioActual.findNegocioId()
                .orElseThrow(() -> new BalanceException(
                        "BALANCE_SIN_NEGOCIO",
                        "No hay un negocio activo para generar el balance."));

        List<LocalDate> fechasSinCotizacion =
                balanceMovimientoPort.findFechasSinCotizacion(negocioId, filtro);
        if (!fechasSinCotizacion.isEmpty()) {
            cotizacionOnDemand.asegurar(
                    negocioId,
                    filtro.monedaRegistroId(),
                    filtro.monedaExpresionId(),
                    filtro.desde(),
                    filtro.hasta(),
                    fechasSinCotizacion);
            fechasSinCotizacion =
                    balanceMovimientoPort.findFechasSinCotizacion(negocioId, filtro);
        }
        if (!fechasSinCotizacion.isEmpty()) {
            throw new BalanceException(
                    "BALANCE_COTIZACION_FALTANTE",
                    "Faltan cotizaciones para " + fechasSinCotizacion.size()
                            + " fecha(s): " + fechasSinCotizacion);
        }

        String clave = negocioId + "|" + filtro + "|"
                + balanceMovimientoPort.watermark(negocioId, filtro);
        Balance cacheado = balanceCache.get(clave);
        if (cacheado != null) {
            return cacheado;
        }

        List<Cuenta> cuentas = cuentaRepository.findAllByNegocioIdOrdered(negocioId);
        List<SaldoCuenta> movimientos = balanceMovimientoPort.findSaldos(negocioId, filtro);
        List<SaldoCuenta> apertura = filtro.incluyeApertura()
                ? balanceMovimientoPort.findSaldosApertura(negocioId, filtro)
                : List.of();

        Balance balance = balanceCalculator.calcular(cuentas, movimientos, apertura, filtro);
        balanceCache.put(clave, balance);
        return balance;
    }

}
