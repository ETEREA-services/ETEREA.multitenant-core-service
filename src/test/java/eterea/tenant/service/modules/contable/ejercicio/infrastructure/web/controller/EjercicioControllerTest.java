package eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.ejercicio.application.service.EjercicioService;
import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioSugerido;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.mapper.EjercicioDtoMapper;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EjercicioControllerTest {

    @Mock
    private EjercicioService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        EjercicioController controller = new EjercicioController(
                service, new EjercicioDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new EjercicioExceptionHandler())
                .build();
    }

    @Test
    void listaEjerciciosConEstadoDerivado() throws Exception {
        LocalDate hoy = LocalDate.now();
        when(service.findAll()).thenReturn(List.of(Ejercicio.builder()
                .ejercicioId(1L)
                .nombre("Ejercicio en curso")
                .fechaInicio(hoy.withDayOfYear(1))
                .fechaFin(hoy.withDayOfYear(hoy.lengthOfYear()))
                .build()));

        mockMvc.perform(get("/api/tenant/core/ejercicio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ejercicioId").value(1))
                .andExpect(jsonPath("$[0].estado").value("EN_CURSO"))
                .andExpect(jsonPath("$[0].bloqueado").value(false));
    }

    @Test
    void devuelveLaSugerenciaDelSiguienteEjercicio() throws Exception {
        when(service.sugerirSiguiente()).thenReturn(new EjercicioSugerido(
                true,
                "Ejercicio Económico 2027",
                LocalDate.of(2027, 1, 1),
                LocalDate.of(2027, 12, 31),
                "ULTIMO_EJERCICIO",
                null));

        mockMvc.perform(get("/api/tenant/core/ejercicio/siguiente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.disponible").value(true))
                .andExpect(jsonPath("$.fechaInicio").value("2027-01-01"))
                .andExpect(jsonPath("$.origen").value("ULTIMO_EJERCICIO"));
    }

    @Test
    void creaUnEjercicio() throws Exception {
        when(service.create(any())).thenReturn(Ejercicio.builder()
                .ejercicioId(7L)
                .nombre("Ejercicio Económico 2027")
                .fechaInicio(LocalDate.of(2027, 1, 1))
                .fechaFin(LocalDate.of(2027, 12, 31))
                .build());

        mockMvc.perform(post("/api/tenant/core/ejercicio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Ejercicio Económico 2027",
                                  "fechaInicio": "2027-01-01",
                                  "fechaFin": "2027-12-31"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ejercicioId").value(7))
                .andExpect(jsonPath("$.nombre").value("Ejercicio Económico 2027"));
    }

    @Test
    void traduceUnSolapamientoAConflicto() throws Exception {
        when(service.create(any())).thenThrow(new EjercicioInvalidoException(
                "EJERCICIO_SOLAPADO", "El ejercicio se superpone con otro existente."));

        mockMvc.perform(post("/api/tenant/core/ejercicio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Ejercicio Económico 2027",
                                  "fechaInicio": "2027-01-01",
                                  "fechaFin": "2027-12-31"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.codigo").value("EJERCICIO_SOLAPADO"));
    }

    @Test
    void eliminaUnEjercicio() throws Exception {
        mockMvc.perform(delete("/api/tenant/core/ejercicio/3"))
                .andExpect(status().isNoContent());

        verify(service).delete(3L);
    }

}
