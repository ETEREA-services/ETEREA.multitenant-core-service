package eterea.tenant.service.modules.contable.balance.reporte.application.usecases;

import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;
import eterea.tenant.service.modules.contable.balance.domain.model.Balance;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceCuenta;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceModo;
import eterea.tenant.service.modules.contable.balance.domain.model.BalanceTotales;
import eterea.tenant.service.modules.contable.balance.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.balance.domain.ports.in.GenerarBalanceUseCase;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.MonedaQueryPort;
import eterea.tenant.service.modules.contable.balance.reporte.domain.ports.in.GenerarBalanceReporteUseCase;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Convierte el {@link Balance} de dominio en el contrato común de reportes.
 *
 * <p>El mismo {@link ReporteTabla} alimenta la vista previa (JSON) y el PDF.
 * Los importes se formatean en es-AR; los códigos van sin separador de miles.
 * El balance general agrega una columna "Diferencia" y el total la muestra
 * cuando el libro no cuadra (D8-A).</p>
 */
@Component
@RequiredArgsConstructor
public class GenerarBalanceReporteUseCaseImpl implements GenerarBalanceReporteUseCase {

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final List<ReporteColumna> COLUMNAS_ESTRUCTURA = List.of(
            new ReporteColumna("codigo", "Código", ReporteAlineacion.DERECHA, 0.14f, false,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("cuenta", "Cuenta", ReporteAlineacion.IZQUIERDA, 0.43f, true,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("deudores", "Deudores", ReporteAlineacion.DERECHA, 0.15f, false,
                    ReporteTipoColumna.NUMERO),
            new ReporteColumna("acreedores", "Acreedores", ReporteAlineacion.DERECHA, 0.15f, false,
                    ReporteTipoColumna.NUMERO),
            new ReporteColumna("diferencia", "Diferencia", ReporteAlineacion.DERECHA, 0.13f, false,
                    ReporteTipoColumna.NUMERO));

    private static final List<ReporteColumna> COLUMNAS_POR_CUENTA = List.of(
            new ReporteColumna("codigo", "Código", ReporteAlineacion.DERECHA, 0.18f, false,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("cuenta", "Cuenta", ReporteAlineacion.IZQUIERDA, 0.62f, true,
                    ReporteTipoColumna.TEXTO),
            new ReporteColumna("saldo", "Saldo", ReporteAlineacion.DERECHA, 0.20f, false,
                    ReporteTipoColumna.NUMERO));

    private final GenerarBalanceUseCase generarBalance;
    private final NegocioActual negocioActual;
    private final MonedaQueryPort monedaQuery;

    @Override
    public ReporteTabla generar(BalanceFiltro filtro) {
        Balance balance = generarBalance.generar(filtro);
        NegocioActualInfo negocio = negocioActual.findActual()
                .orElseThrow(() -> new BalanceException(
                        "BALANCE_SIN_NEGOCIO",
                        "No hay un negocio activo para generar el balance."));

        MonedaInfo registro = moneda(filtro.monedaRegistroId());
        MonedaInfo expresion = moneda(filtro.monedaExpresionId());

        boolean estructura = filtro.modo() == BalanceModo.ESTRUCTURA;
        String titulo = estructura ? "Balance General" : "Saldos Contables";
        String codigo = estructura ? "balance-general" : "saldos-contables";
        String subtitulo = "Período: " + FECHA.format(filtro.desde())
                + " - " + FECHA.format(filtro.hasta())
                + " · Registrado en " + registro.simbolo()
                + " · Expresado en " + expresion.simbolo();

        List<ReporteFila> filas = estructura
                ? filasEstructura(balance)
                : filasPorCuenta(balance);

        return new ReporteTabla(
                new ReporteCabecera(codigo, titulo, negocio.nombre(), filtro.hasta(), subtitulo),
                estructura ? COLUMNAS_ESTRUCTURA : COLUMNAS_POR_CUENTA,
                filas,
                balance.advertencias());
    }

    private MonedaInfo moneda(Integer monedaId) {
        return monedaQuery.findById(monedaId)
                .orElseThrow(() -> new BalanceException(
                        "BALANCE_MONEDA_INVALIDA",
                        "La moneda " + monedaId + " no existe en el catálogo del negocio."));
    }

    private List<ReporteFila> filasEstructura(Balance balance) {
        List<ReporteFila> filas = new ArrayList<>();
        for (BalanceCuenta cuenta : balance.filas()) {
            filas.add(new ReporteFila(
                    cuenta.nivel(),
                    cuenta.negrita(),
                    List.of(
                            String.valueOf(cuenta.numeroCuenta()),
                            cuenta.nombre(),
                            formatear(cuenta.saldoDeudor()),
                            formatear(cuenta.saldoAcreedor()),
                            "")));
        }
        BalanceTotales totales = balance.totales();
        filas.add(new ReporteFila(
                1,
                true,
                List.of(
                        "",
                        "TOTALES",
                        formatear(totales.deudores()),
                        formatear(totales.acreedores()),
                        formatearSigno(totales.diferencia()))));
        return filas;
    }

    private List<ReporteFila> filasPorCuenta(Balance balance) {
        List<ReporteFila> filas = new ArrayList<>();
        for (BalanceCuenta cuenta : balance.filas()) {
            BigDecimal saldo = cuenta.saldoDeudor().subtract(cuenta.saldoAcreedor());
            filas.add(new ReporteFila(
                    cuenta.nivel(),
                    cuenta.negrita(),
                    List.of(
                            String.valueOf(cuenta.numeroCuenta()),
                            cuenta.nombre(),
                            formatearSigno(saldo))));
        }
        BalanceTotales totales = balance.totales();
        BigDecimal total = totales.deudores().subtract(totales.acreedores());
        filas.add(new ReporteFila(
                1,
                true,
                List.of("", "TOTAL", formatearSigno(total))));
        return filas;
    }

    private static String formatear(BigDecimal valor) {
        return formateador().format(valor.setScale(2, RoundingMode.HALF_UP));
    }

    private static String formatearSigno(BigDecimal valor) {
        BigDecimal redondeado = valor.setScale(2, RoundingMode.HALF_UP);
        if (redondeado.signum() < 0) {
            return "(" + formateador().format(redondeado.abs()) + ")";
        }
        return formateador().format(redondeado);
    }

    private static DecimalFormat formateador() {
        DecimalFormatSymbols simbolos = DecimalFormatSymbols.getInstance(
                Locale.forLanguageTag("es-AR"));
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        formato.setRoundingMode(RoundingMode.HALF_UP);
        return formato;
    }

}
