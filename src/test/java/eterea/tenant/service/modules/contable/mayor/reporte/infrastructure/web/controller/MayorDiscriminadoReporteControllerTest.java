package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.mayor.reporte.domain.ports.in.GenerarMayorDiscriminadoReporteUseCase;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.MayorExceptionHandler;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MayorDiscriminadoReporteControllerTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    @Mock
    private GenerarMayorDiscriminadoReporteUseCase generarMayorDiscriminadoReporte;
    @Mock
    private GenerarReportePdfUseCase generarReportePdf;
    @Mock
    private GenerarReporteXlsxUseCase generarReporteXlsx;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MayorDiscriminadoReporteController controller =
                new MayorDiscriminadoReporteController(
                        generarMayorDiscriminadoReporte,
                        generarReportePdf,
                        generarReporteXlsx,
                        new ReporteDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new MayorExceptionHandler())
                .build();
    }

    @Test
    void devuelveLaVistaPrevia() throws Exception {
        when(generarMayorDiscriminadoReporte.generar(any())).thenReturn(tabla());

        mockMvc.perform(post("/api/tenant/core/reporte/mayor-discriminado/datos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Mayor Discriminado"))
                .andExpect(jsonPath("$.filas[0].celdas[0]").value("11-11-2026"));
    }

    @Test
    void devuelveElPdfConNombreDeArchivo() throws Exception {
        when(generarMayorDiscriminadoReporte.generar(any())).thenReturn(tabla());
        when(generarReportePdf.generar(any())).thenReturn("%PDF-1.4".getBytes());

        mockMvc.perform(post("/api/tenant/core/reporte/mayor-discriminado/pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString(
                                "mayor-discriminado-tsa-agencia-11-11-2026-11-11-2026.pdf")));
    }

    private static ReporteTabla tabla() {
        return new ReporteTabla(
                new ReporteCabecera(
                        "mayor-discriminado", "Mayor Discriminado", "TSA Agencia", DIA,
                        "Cuentas: 2"),
                List.of(new ReporteColumna(
                        "fecha", "Fecha", ReporteAlineacion.IZQUIERDA, 0.1f, false)),
                List.of(new ReporteFila(1, false, List.of("11-11-2026"))));
    }

    private static String body() {
        return """
                {
                  "cuentas": [21201001, 21101001],
                  "desde": "2026-11-11",
                  "hasta": "2026-11-11",
                  "monedaRegistroId": 1,
                  "monedaExpresionId": 1
                }
                """;
    }

}
