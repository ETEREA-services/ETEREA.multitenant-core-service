package eterea.tenant.service.modules.contable.mayor.application.usecases;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.domain.ports.out.CuentaRepository;
import eterea.tenant.service.modules.contable.mayor.application.service.MayorCalculator;
import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminado;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFila;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.ports.in.GenerarMayorDiscriminadoUseCase;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorControlPort;
import eterea.tenant.service.modules.contable.mayor.domain.ports.out.MayorMovimientoPort;
import eterea.tenant.service.modules.contable.moneda.domain.exception.MonedaException;
import eterea.tenant.service.modules.contable.moneda.domain.ports.out.CotizacionOnDemandPort;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Orquesta el Mayor Discriminado: valida las cuentas contra el plan, asegura
 * cotizaciones y devuelve las filas con las advertencias de D9-C.
 */
@Component
@RequiredArgsConstructor
public class GenerarMayorDiscriminadoUseCaseImpl implements GenerarMayorDiscriminadoUseCase {

    private final CuentaRepository cuentaRepository;
    private final MayorMovimientoPort mayorMovimientoPort;
    private final MayorControlPort mayorControlPort;
    private final CotizacionOnDemandPort cotizacionOnDemand;
    private final MayorCalculator mayorCalculator;
    private final NegocioActual negocioActual;

    @Override
    public MayorDiscriminado generar(MayorDiscriminadoFiltro filtro) {
        int negocioId = negocioActual.findNegocioId()
                .orElseThrow(() -> new MayorException(
                        "MAYOR_SIN_NEGOCIO",
                        "No hay un negocio activo para generar el Mayor Discriminado."));

        Map<Long, Cuenta> plan = cuentaRepository.findAllByNegocioIdOrdered(negocioId)
                .stream()
                .collect(Collectors.toMap(
                        Cuenta::getNumeroCuenta, Function.identity(), (primera, segunda) -> primera));

        List<Long> validas = new ArrayList<>();
        List<String> advertencias = new ArrayList<>();
        for (Long cuenta : filtro.cuentas()) {
            if (plan.containsKey(cuenta)) {
                validas.add(cuenta);
            } else {
                advertencias.add("La cuenta " + cuenta + " no existe en el plan del negocio.");
            }
        }
        if (validas.isEmpty()) {
            return new MayorDiscriminado(List.of(), advertencias);
        }

        MayorDiscriminadoFiltro valido = new MayorDiscriminadoFiltro(
                validas,
                filtro.desde(),
                filtro.hasta(),
                filtro.monedaRegistroId(),
                filtro.monedaExpresionId());

        asegurarCotizaciones(negocioId, valido);

        List<MayorDiscriminadoFila> filas = mayorCalculator.calcularDiscriminado(
                mayorMovimientoPort.findDetalleDiscriminado(negocioId, valido));
        MayorControl control = mayorControlPort.findControlDiscriminado(negocioId, valido);
        if (control.movimientosExcluidos() > 0) {
            String codigos = control.comprobantesSinTipo().isEmpty()
                    ? ""
                    : ": " + control.comprobantesSinTipo();
            advertencias.add("Se excluyeron " + control.movimientosExcluidos()
                    + " movimientos con comprobante sin tipo" + codigos + ".");
        }

        return new MayorDiscriminado(filas, advertencias);
    }

    private void asegurarCotizaciones(int negocioId, MayorDiscriminadoFiltro filtro) {
        if (filtro.esMonedaBase()) {
            return;
        }

        List<LocalDate> faltantes =
                mayorMovimientoPort.findFechasSinCotizacionDiscriminado(negocioId, filtro);
        if (faltantes.isEmpty()) {
            return;
        }

        try {
            cotizacionOnDemand.asegurar(
                    negocioId,
                    filtro.monedaRegistroId(),
                    filtro.monedaExpresionId(),
                    filtro.desde().minusDays(1),
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

        faltantes = mayorMovimientoPort.findFechasSinCotizacionDiscriminado(negocioId, filtro);
        if (!faltantes.isEmpty()) {
            throw new MayorException(
                    "MAYOR_COTIZACION_FALTANTE",
                    "Faltan cotizaciones para " + faltantes.size()
                            + " fecha(s): " + faltantes);
        }
    }

}
