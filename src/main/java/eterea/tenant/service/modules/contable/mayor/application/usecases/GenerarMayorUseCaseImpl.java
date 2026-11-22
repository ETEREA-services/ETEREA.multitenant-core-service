package eterea.tenant.service.modules.contable.mayor.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.mayor.application.service.MayorCalculator;
import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorCuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDetalle;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.ports.in.GenerarMayorUseCase;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorControlPort;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorMovimientoPort;
import eterea.tenant.service.modules.contable.moneda.domain.exception.MonedaException;
import eterea.tenant.service.modules.contable.moneda.domain.ports.out.CotizacionOnDemandPort;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Orquesta el Mayor de una cuenta: resuelve negocio y cuenta, asegura las
 * cotizaciones (D10-A), lee el detalle y el control, y delega el cálculo.
 */
@Component
@RequiredArgsConstructor
public class GenerarMayorUseCaseImpl implements GenerarMayorUseCase {

    private final CuentaRepository cuentaRepository;
    private final MayorMovimientoPort mayorMovimientoPort;
    private final MayorControlPort mayorControlPort;
    private final CotizacionOnDemandPort cotizacionOnDemand;
    private final MayorCalculator mayorCalculator;
    private final NegocioActual negocioActual;

    @Override
    public MayorCuenta generar(MayorFiltro filtro) {
        int negocioId = negocioActual.findNegocioId()
                .orElseThrow(() -> new MayorException(
                        "MAYOR_SIN_NEGOCIO",
                        "No hay un negocio activo para generar el Mayor."));

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(filtro.cuenta())
                .filter(encontrada -> Objects.equals(encontrada.getNegocioId(), negocioId))
                .orElseThrow(() -> new MayorException(
                        "MAYOR_CUENTA_NO_EXISTE",
                        "La cuenta " + filtro.cuenta() + " no existe en el plan del negocio."));

        asegurarCotizaciones(negocioId, filtro);

        BigDecimal saldoAnterior = filtro.sinSaldoAnterior()
                ? BigDecimal.ZERO
                : mayorMovimientoPort.findSaldoAnterior(negocioId, filtro);
        List<MayorDetalle> detalles = mayorMovimientoPort.findDetalle(negocioId, filtro);
        MayorControl control = mayorControlPort.findControl(negocioId, filtro);

        return mayorCalculator.calcular(cuenta, saldoAnterior, detalles, control);
    }

    private void asegurarCotizaciones(int negocioId, MayorFiltro filtro) {
        if (filtro.esMonedaBase()) {
            return;
        }

        List<LocalDate> faltantes =
                mayorMovimientoPort.findFechasSinCotizacion(negocioId, filtro);
        if (faltantes.isEmpty()) {
            return;
        }

        try {
            cotizacionOnDemand.asegurar(
                    negocioId,
                    filtro.monedaRegistroId(),
                    filtro.monedaExpresionId(),
                    filtro.fechaApertura(),
                    filtro.hasta(),
                    faltantes);
        } catch (MonedaException exception) {
            throw new MayorException(
                    "MONEDA_INVALIDA".equals(exception.getCodigo())
                            ? "MAYOR_MONEDA_INVALIDA"
                            : "MAYOR_COTIZACION_FALTANTE",
                    exception.getMessage(),
                    exception);
        }

        faltantes = mayorMovimientoPort.findFechasSinCotizacion(negocioId, filtro);
        if (!faltantes.isEmpty()) {
            throw new MayorException(
                    "MAYOR_COTIZACION_FALTANTE",
                    "Faltan cotizaciones para " + faltantes.size()
                            + " fecha(s): " + faltantes);
        }
    }

}
