package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.coeficienteinflacion.application.service.CoeficienteInflacionService;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.exception.CoeficienteInflacionInvalidaException;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacion;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.mapper.CoeficienteInflacionDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CoeficienteInflacionControllerTest {

    @Mock
    private CoeficienteInflacionService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        CoeficienteInflacionController controller =
                new CoeficienteInflacionController(service, new CoeficienteInflacionDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CoeficienteInflacionExceptionHandler())
                .build();
    }

    @Test
    void listaLosAniosConDatos() throws Exception {
        when(service.findAnios()).thenReturn(List.of(2026, 2025));

        mockMvc.perform(get("/api/tenant/core/coeficiente-inflacion/anios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(2026))
                .andExpect(jsonPath("$[1]").value(2025));
    }

    @Test
    void devuelveDoceMesesConLosFaltantesEnNull() throws Exception {
        when(service.findAnio(2026)).thenReturn(CoeficienteInflacionAnio.of(2026,
                List.of(registro(1, "1.234500"))));

        mockMvc.perform(get("/api/tenant/core/coeficiente-inflacion/anio/2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.anho").value(2026))
                .andExpect(jsonPath("$.meses.length()").value(12))
                .andExpect(jsonPath("$.meses[0].mes").value(1))
                .andExpect(jsonPath("$.meses[0].coeficiente").value(1.2345))
                .andExpect(jsonPath("$.meses[0].cargado").value(true))
                .andExpect(jsonPath("$.meses[1].coeficiente").doesNotExist())
                .andExpect(jsonPath("$.meses[1].cargado").value(false))
                .andExpect(jsonPath("$.cargados").value(1))
                .andExpect(jsonPath("$.total").value(12));
    }

    @Test
    void guardaUnAnioCompleto() throws Exception {
        when(service.save(eq(2026), anyList())).thenReturn(CoeficienteInflacionAnio.of(2026,
                List.of(registro(1, "1.250000"))));

        mockMvc.perform(put("/api/tenant/core/coeficiente-inflacion/anio/2026")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "meses": [
                                    { "mes": 1, "coeficiente": 1.25 },
                                    { "mes": 2, "coeficiente": null }
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.anho").value(2026))
                .andExpect(jsonPath("$.cargados").value(1));

        verify(service).save(eq(2026), anyList());
    }

    @Test
    void traduceUnMesDuplicadoABadRequest() throws Exception {
        when(service.findAnio(2026)).thenThrow(new CoeficienteInflacionInvalidaException(
                "COEFICIENTE_MES_DUPLICADO", "El mes 1 está repetido."));

        mockMvc.perform(get("/api/tenant/core/coeficiente-inflacion/anio/2026"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("COEFICIENTE_MES_DUPLICADO"));
    }

    @Test
    void traduceUnConflictoAConflict() throws Exception {
        when(service.save(eq(2026), anyList())).thenThrow(
                new CoeficienteInflacionInvalidaException(
                        "COEFICIENTE_CONFLICTO", "Ya existe el coeficiente."));

        mockMvc.perform(put("/api/tenant/core/coeficiente-inflacion/anio/2026")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "meses": [ { "mes": 1, "coeficiente": 1.25 } ] }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.codigo").value("COEFICIENTE_CONFLICTO"));
    }

    private static CoeficienteInflacion registro(int mes, String coeficiente) {
        return CoeficienteInflacion.builder()
                .coeficienteInflacionId((long) mes)
                .anho(2026)
                .mes(mes)
                .coeficiente(new BigDecimal(coeficiente))
                .build();
    }

}
