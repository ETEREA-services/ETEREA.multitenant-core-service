package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorControl;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorModulo;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.model.MayorReporte;
import eterea.tenant.service.modules.contable.mayor.reporte.domain.ports.in.GenerarMayorReporteUseCase;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.MayorExceptionHandler;
import eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web.mapper.MayorDtoMapper;
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
class MayorReporteControllerTest {

    private static final LocalDate DIA = LocalDate.of(2026, 11, 11);

    @Mock
    private GenerarMayorReporteUseCase generarMayorReporte;
    @Mock
    private GenerarReportePdfUseCase generarReportePdf;
    @Mock
    private GenerarReporteXlsxUseCase generarReporteXlsx;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MayorReporteController controller = new MayorReporteController(
                generarMayorReporte,
                generarReportePdf,
                generarReporteXlsx,
                new ReporteDtoMapper(),
                new MayorDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new MayorExceptionHandler())
                .build();
    }

    @Test
    void devuelveReporteYcontrol() throws Exception {
        when(generarMayorReporte.generar(any())).thenReturn(reporte());

        mockMvc.perform(post("/api/tenant/core/reporte/mayor/datos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reporte.titulo").value("Mayor General"))
                .andExpect(jsonPath("$.reporte.subtitulo").value("Período de prueba"))
                .andExpect(jsonPath("$.control.totalMovimientos").value(2))
                .andExpect(jsonPath("$.control.modulos[0].nombre").value("Ventas"));
    }

    @Test
    void devuelveElPdfConNombreDeArchivo() throws Exception {
        when(generarMayorReporte.generar(any())).thenReturn(reporte());
        when(generarReportePdf.generar(any())).thenReturn("%PDF-1.4".getBytes());

        mockMvc.perform(post("/api/tenant/core/reporte/mayor/pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString(
                                "mayor-tsa-agencia-11-11-2026-11-11-2026.pdf")));
    }

    @Test
    void devuelveElXlsxConContentType() throws Exception {
        when(generarMayorReporte.generar(any())).thenReturn(reporte());
        when(generarReporteXlsx.generar(any())).thenReturn(new byte[]{1, 2, 3});

        mockMvc.perform(post("/api/tenant/core/reporte/mayor/xlsx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

    @Test
    void traduceElErrorDeCotizacion() throws Exception {
        when(generarMayorReporte.generar(any()))
                .thenThrow(new MayorException(
                        "MAYOR_COTIZACION_FALTANTE", "Faltan cotizaciones."));

        mockMvc.perform(post("/api/tenant/core/reporte/mayor/datos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.codigo").value("MAYOR_COTIZACION_FALTANTE"));
    }

    private static MayorReporte reporte() {
        MayorControl control = new MayorControl(
                2, 0, List.of(new MayorModulo(1, "Ventas", 2)), List.of());
        ReporteTabla tabla = new ReporteTabla(
                new ReporteCabecera("mayor", "Mayor General", "TSA Agencia", DIA,
                        "Período de prueba"),
                List.of(new ReporteColumna(
                        "fecha", "Fecha", ReporteAlineacion.IZQUIERDA, 0.1f, false)),
                List.of(new ReporteFila(1, false, List.of("11-11-2026"))));
        return new MayorReporte(tabla, control);
    }

    private static String body() {
        return """
                {
                  "cuenta": 21201001,
                  "desde": "2026-11-11",
                  "hasta": "2026-11-11",
                  "sinSaldoAnterior": false,
                  "monedaRegistroId": 1,
                  "monedaExpresionId": 1
                }
                """;
    }

}
