package eterea.tenant.service.modules.contable.balance.reporte.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;
import eterea.tenant.service.modules.contable.balance.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.balance.domain.ports.out.MonedaQueryPort;
import eterea.tenant.service.modules.contable.balance.reporte.domain.ports.in.GenerarBalanceReporteUseCase;
import eterea.tenant.service.modules.contable.balance.reporte.infrastructure.web.BalanceExceptionHandler;
import eterea.tenant.service.modules.reporte.api.GenerarReportePdfUseCase;
import eterea.tenant.service.modules.reporte.api.GenerarReporteXlsxUseCase;
import eterea.tenant.service.modules.reporte.api.ReporteAlineacion;
import eterea.tenant.service.modules.reporte.api.ReporteCabecera;
import eterea.tenant.service.modules.reporte.api.ReporteColumna;
import eterea.tenant.service.modules.reporte.api.ReporteDtoMapper;
import eterea.tenant.service.modules.reporte.api.ReporteFila;
import eterea.tenant.service.modules.reporte.api.ReporteTabla;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BalanceReporteControllerTest {

    @Mock
    private GenerarBalanceReporteUseCase generarBalanceReporte;
    @Mock
    private GenerarReportePdfUseCase generarReportePdf;
    @Mock
    private GenerarReporteXlsxUseCase generarReporteXlsx;
    @Mock
    private MonedaQueryPort monedaQuery;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        BalanceReporteController controller = new BalanceReporteController(
                generarBalanceReporte,
                generarReportePdf,
                generarReporteXlsx,
                new ReporteDtoMapper(),
                monedaQuery);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new BalanceExceptionHandler())
                .build();
    }

    @Test
    void devuelveLaVistaPreviaConSubtituloYAdvertencias() throws Exception {
        when(generarBalanceReporte.generar(any())).thenReturn(tabla());

        mockMvc.perform(post("/api/tenant/core/reporte/balance/datos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Balance General"))
                .andExpect(jsonPath("$.subtitulo").value(
                        "Período: 31-10-2026 - 31-10-2026 · Registrado en ARS · Expresado en ARS"))
                .andExpect(jsonPath("$.advertencias[0]").value("El balance no cuadra."))
                .andExpect(jsonPath("$.filas[0].celdas[0]").value("10000000"))
                .andExpect(jsonPath("$.filas[1].celdas[1]").value("TOTALES"));
    }

    @Test
    void devuelveElPdfConNombreDeArchivo() throws Exception {
        when(generarBalanceReporte.generar(any())).thenReturn(tabla());
        when(generarReportePdf.generar(any())).thenReturn("%PDF-1.4".getBytes());

        mockMvc.perform(post("/api/tenant/core/reporte/balance/pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString("balance-general-tsa-agencia-31-10-2026-31-10-2026.pdf")));
    }

    @Test
    void devuelveElXlsxConNombreDeArchivo() throws Exception {
        when(generarBalanceReporte.generar(any())).thenReturn(tabla());
        when(generarReporteXlsx.generar(any())).thenReturn(new byte[]{1, 2, 3});

        mockMvc.perform(post("/api/tenant/core/reporte/balance/xlsx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString("balance-general-tsa-agencia-31-10-2026-31-10-2026.xlsx")));
    }

    @Test
    void devuelveElCatalogoDeMonedas() throws Exception {
        when(monedaQuery.findAll()).thenReturn(List.of(
                new MonedaInfo(1, "Peso", "ARS", true),
                new MonedaInfo(2, "Dólar", "USD", false)));

        mockMvc.perform(get("/api/tenant/core/reporte/balance/monedas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monedas[0].simbolo").value("ARS"))
                .andExpect(jsonPath("$.monedas[0].esBase").value(true))
                .andExpect(jsonPath("$.monedas[1].simbolo").value("USD"));
    }

    @Test
    void traduceLaFaltaDeCotizacionAUnprocessableEntity() throws Exception {
        when(generarBalanceReporte.generar(any())).thenThrow(new BalanceException(
                "BALANCE_COTIZACION_FALTANTE",
                "Faltan cotizaciones para 1 fecha(s): [2026-10-31]"));

        mockMvc.perform(post("/api/tenant/core/reporte/balance/datos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.codigo").value("BALANCE_COTIZACION_FALTANTE"));
    }

    private static String body() {
        return """
                {
                  "desde": "2026-10-31",
                  "hasta": "2026-10-31",
                  "incluyeApertura": false,
                  "incluyeInflacion": true,
                  "modo": "ESTRUCTURA",
                  "monedaRegistroId": 1,
                  "monedaExpresionId": 1
                }
                """;
    }

    private static ReporteTabla tabla() {
        return new ReporteTabla(
                new ReporteCabecera(
                        "balance-general",
                        "Balance General",
                        "TSA Agencia",
                        LocalDate.of(2026, 10, 31),
                        "Período: 31-10-2026 - 31-10-2026 · Registrado en ARS · Expresado en ARS"),
                List.of(
                        new ReporteColumna("codigo", "Código", ReporteAlineacion.DERECHA, 0.2f, false),
                        new ReporteColumna("cuenta", "Cuenta", ReporteAlineacion.IZQUIERDA, 0.8f, true)),
                List.of(
                        new ReporteFila(1, false, List.of("10000000", "ACTIVO")),
                        new ReporteFila(1, true, List.of("", "TOTALES"))),
                List.of("El balance no cuadra."));
    }

}
