package eterea.tenant.service.modules.contable.mayor.reporte.application.usecases;

import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminado;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFila;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.ports.in.GenerarMayorDiscriminadoUseCase;
import eterea.tenant.service.modules.contable.mayor.reporte.application.service.MayorReporteFormato;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.ports.in.GenerarMayorDiscriminadoReporteUseCase;
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
 * Arma la tabla del Mayor Discriminado.
 */
@Component
@RequiredArgsConstructor
public class GenerarMayorDiscriminadoReporteUseCaseImpl
        implements GenerarMayorDiscriminadoReporteUseCase {

    private static final List<ReporteColumna> COLUMNAS = List.of(
            new ReporteColumna("fecha", "Fecha", ReporteAlineacion.IZQUIERDA, 0.12f, false,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("cuenta", "Cuenta", ReporteAlineacion.DERECHA, 0.12f, false,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("nombreCuenta", "Nombre", ReporteAlineacion.IZQUIERDA, 0.13f,
                    false, ReporteTipoColumna.TEXTO),
            new ReporteColumna("comprobante", "Comprob.", ReporteAlineacion.IZQUIERDA, 0.11f,
                    false, ReporteTipoColumna.TEXTO),
            new ReporteColumna("concepto", "Concepto", ReporteAlineacion.IZQUIERDA, 0.12f,
                    false, ReporteTipoColumna.TEXTO),
            new ReporteColumna("asiento", "Asiento", ReporteAlineacion.DERECHA, 0.09f, false,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("debe", "Debe", ReporteAlineacion.DERECHA, 0.13f, false,
                    ReporteTipoColumna.NUMERO),
            new ReporteColumna("haber", "Haber", ReporteAlineacion.DERECHA, 0.13f, false,
                    ReporteTipoColumna.NUMERO),
            new ReporteColumna("proveedor", "Proveedor", ReporteAlineacion.IZQUIERDA, 0.13f,
                    false, ReporteTipoColumna.TEXTO));

    private final GenerarMayorDiscriminadoUseCase generarMayorDiscriminado;
    private final NegocioActual negocioActual;
    private final MonedaQueryPort monedaQuery;

    @Override
    public ReporteTabla generar(MayorDiscriminadoFiltro filtro) {
        MayorDiscriminado discriminado = generarMayorDiscriminado.generar(filtro);
        NegocioActualInfo negocio = negocioActual.findActual()
                .orElseThrow(() -> new MayorException(
                        "MAYOR_SIN_NEGOCIO",
                        "No hay un negocio activo para generar el Mayor Discriminado."));
        MonedaInfo registro = moneda(filtro.monedaRegistroId());
        MonedaInfo expresion = moneda(filtro.monedaExpresionId());

        String subtitulo = "Cuentas: " + filtro.cuentas().size()
                + " · Período: " + MayorReporteFormato.fecha(filtro.desde())
                + " - " + MayorReporteFormato.fecha(filtro.hasta())
                + " · Registrado en " + registro.simbolo()
                + " · Expresado en " + expresion.simbolo();

        List<ReporteFila> filas = new ArrayList<>();
        for (MayorDiscriminadoFila fila : discriminado.filas()) {
            filas.add(new ReporteFila(1, false, List.of(
                    MayorReporteFormato.fecha(fila.fecha()),
                    String.valueOf(fila.numeroCuenta()),
                    MayorReporteFormato.texto(fila.nombreCuenta()),
                    MayorReporteFormato.texto(fila.comprobante()),
                    MayorReporteFormato.texto(fila.concepto()),
                    MayorReporteFormato.asiento(fila.asiento()),
                    MayorReporteFormato.importe(fila.debe()),
                    MayorReporteFormato.importe(fila.haber()),
                    MayorReporteFormato.texto(fila.proveedor()))));
        }

        List<String> advertencias = new ArrayList<>(discriminado.advertencias());
        if (filas.isEmpty()) {
            advertencias.add(
                    "No hay movimientos para las cuentas y el período seleccionados.");
        }

        return new ReporteTabla(
                new ReporteCabecera(
                        "mayor-discriminado",
                        "Mayor Discriminado",
                        negocio.nombre(),
                        filtro.hasta(),
                        subtitulo),
                COLUMNAS,
                filas,
                advertencias);
    }

    private MonedaInfo moneda(Integer monedaId) {
        return monedaQuery.findById(monedaId)
                .orElseThrow(() -> new MayorException(
                        "MAYOR_MONEDA_INVALIDA",
                        "La moneda " + monedaId + " no existe en el catálogo del negocio."));
    }

}
