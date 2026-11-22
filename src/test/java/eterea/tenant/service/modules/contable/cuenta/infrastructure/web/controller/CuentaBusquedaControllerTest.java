package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.cuenta.application.service.CuentaService;
import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;
import eterea.tenant.service.modules.contable.cuenta.infrastructure.web.mapper.CuentaDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CuentaBusquedaControllerTest {

    @Mock
    private CuentaService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CuentaController(service, new CuentaDtoMapper()))
                .build();
    }

    @Test
    void devuelveElTextoLegacyNombreYCuenta() throws Exception {
        when(service.buscarImputables("caja")).thenReturn(List.of(
                Cuenta.builder()
                        .numeroCuenta(11101001L)
                        .nombre("Caja")
                        .negocioId(54)
                        .build()));

        mockMvc.perform(post("/api/tenant/core/cuenta/buscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"texto\":\"caja\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCuenta").value(11101001))
                .andExpect(jsonPath("$[0].descripcion").value("Caja (11101001)"));
    }

}
