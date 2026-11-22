package eterea.tenant.service.modules.contable.moneda.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.moneda.domain.model.MonedaInfo;
import eterea.tenant.service.modules.contable.moneda.domain.ports.out.MonedaQueryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MonedaControllerTest {

    @Mock
    private MonedaQueryPort monedaQuery;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new MonedaController(monedaQuery))
                .build();
    }

    @Test
    void devuelveElCatalogoDelTenant() throws Exception {
        when(monedaQuery.findAll()).thenReturn(List.of(
                new MonedaInfo(1, "Peso Argentino", "ARS", true),
                new MonedaInfo(2, "Dólar", "USD", false)));

        mockMvc.perform(get("/api/tenant/core/moneda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monedas[0].simbolo").value("ARS"))
                .andExpect(jsonPath("$.monedas[1].simbolo").value("USD"));
    }

}
