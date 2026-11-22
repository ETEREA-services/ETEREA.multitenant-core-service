package eterea.tenant.service.modules.contable.mayor.reporte.application.usecases;

import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorCuenta;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorMovimiento;
import eterea.tenant.service.modules.contable.mayor.domain.ports.in.GenerarMayorUseCase;
import eterea.tenant.service.modules.contable.mayor.reporte.application.service.MayorReporteFormato;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.model.MayorReporte;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.ports.in.GenerarMayorReporteUseCase;
import eterea.tenant.service.modules.contable.moneda.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.moneda.domain.ports.out.MonedaQueryPort;
import eterea.tenant.service.modules.platform.api.NegocioActual;
import eterea.tenant.service.modules.platform.api.NegocioActualInfo;
import eterea.tenant.service.modules.reporte.api.ReporteAlineacion;
import eterea.tenant.service.modules.reporte.api.ReporteCabecera;
import eterea.tenant.service.modules.reporte.api.ReporteColumna;
import eterea.tenant.service.modules.reporte.api.ReporteFila;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import eterea.tenant.service.modules.reporte.api.ReporteTipoColumna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Arma la tabla del Mayor General a partir de {@link MayorCuenta}.
 */
@Component
@RequiredArgsConstructor
public class GenerarMayorReporteUseCaseImpl implements GenerarMayorReporteUseCase {

    private static final List<ReporteColumna> COLUMNAS = List.of(
            new ReporteColumna("fecha", "Fecha", ReporteAlineacion.IZQUIERDA, 0.14f, false,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("comprobante", "Comprobante", ReporteAlineacion.IZQUIERDA, 0.17f,
                    false, ReporteTipoColumna.TEXTO),
            new ReporteColumna("concepto", "Concepto", ReporteAlineacion.IZQUIERDA, 0.22f, false,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("asiento", "Asiento", ReporteAlineacion.DERECHA, 0.11f, false,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("debe", "Debe", ReporteAlineacion.DERECHA, 0.10f, false,
                    ReporteTipoColumna.NUMERO),
            new ReporteColumna("haber", "Haber", ReporteAlineacion.DERECHA, 0.10f, false,
                    ReporteTipoColumna.NUMERO),
            new ReporteColumna("saldo", "Saldo", ReporteAlineacion.DERECHA, 0.16f, false,
                    ReporteTipoColumna.NUMERO),
            new ReporteColumna("proveedor", "Proveedor", ReporteAlineacion.IZQUIERDA, 0.15f, false,
                    ReporteTipoColumna.TEXTO));

    private final GenerarMayorUseCase generarMayor;
    private final NegocioActual negocioActual;
    private final MonedaQueryPort monedaQuery;

    @Override
    public MayorReporte generar(MayorFiltro filtro) {
        MayorCuenta mayor = generarMayor.generar(filtro);
        NegocioActualInfo negocio = negocioActual.findActual()
                .orElseThrow(() -> new MayorException(
                        "MAYOR_SIN_NEGOCIO",
                        "No hay un negocio activo para generar el Mayor."));
        MonedaInfo registro = moneda(filtro.monedaRegistroId());
        MonedaInfo expresion = moneda(filtro.monedaExpresionId());

        String subtitulo = "Cuenta " + mayor.numeroCuenta()
                + (mayor.nombre().isBlank() ? "" : " - " + mayor.nombre())
                + " · Período: " + MayorReporteFormato.fecha(filtro.desde())
                + " - " + MayorReporteFormato.fecha(filtro.hasta())
                + " · Registrado en " + registro.simbolo()
                + " · Expresado en " + expresion.simbolo();

        List<ReporteFila> filas = new ArrayList<>();
        if (!filtro.sinSaldoAnterior()) {
            filas.add(new ReporteFila(1, true, List.of(
                    "",
                    "Saldo anterior",
                    "",
                    "",
                    "",
                    "",
                    MayorReporteFormato.formatear(mayor.saldoAnterior()),
                    "")));
        }
        for (MayorMovimiento movimiento : mayor.movimientos()) {
            filas.add(new ReporteFila(1, false, List.of(
                    MayorReporteFormato.fecha(movimiento.fecha()),
                    MayorReporteFormato.texto(movimiento.comprobante()),
                    MayorReporteFormato.texto(movimiento.concepto()),
                    MayorReporteFormato.asiento(movimiento.asiento()),
                    MayorReporteFormato.importe(movimiento.debe()),
                    MayorReporteFormato.importe(movimiento.haber()),
                    MayorReporteFormato.formatear(movimiento.saldo()),
                    MayorReporteFormato.texto(movimiento.proveedor()))));
        }
        filas.add(new ReporteFila(1, true, List.of(
                "",
                "Total",
                "",
                "",
                MayorReporteFormato.formatear(mayor.totalDebe()),
                MayorReporteFormato.formatear(mayor.totalHaber()),
                MayorReporteFormato.formatear(mayor.saldoFinal()),
                "")));

        List<String> advertencias = advertencias(mayor, filtro);
        ReporteTabla tabla = new ReporteTabla(
                new ReporteCabecera(
                        "mayor",
                        "Mayor General",
                        negocio.nombre(),
                        filtro.hasta(),
                        subtitulo),
                COLUMNAS,
                filas,
                advertencias);
        return new MayorReporte(tabla, mayor.control());
    }

    private List<String> advertencias(MayorCuenta mayor, MayorFiltro filtro) {
        List<String> advertencias = new ArrayList<>();
        if (mayor.movimientos().isEmpty()) {
            if (mayor.saldoAnterior().signum() == 0) {
                advertencias.add("La cuenta no tiene movimientos en el período.");
            } else if (!filtro.sinSaldoAnterior()) {
                advertencias.add(
                        "La cuenta no tiene movimientos; se muestra el saldo anterior.");
            }
        }
        MayorControl control = mayor.control();
        if (control.movimientosExcluidos() > 0) {
            String codigos = control.comprobantesSinTipo().isEmpty()
                    ? ""
                    : ": " + control.comprobantesSinTipo();
            advertencias.add("Se excluyeron " + control.movimientosExcluidos()
                    + " movimientos con comprobante sin tipo" + codigos + ".");
        }
        return advertencias;
    }

    private MonedaInfo moneda(Integer monedaId) {
        return monedaQuery.findById(monedaId)
                .orElseThrow(() -> new MayorException(
                        "MAYOR_MONEDA_INVALIDA",
                        "La moneda " + monedaId + " no existe en el catálogo del negocio."));
    }

}
