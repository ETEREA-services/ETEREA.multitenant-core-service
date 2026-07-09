package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.configuration.TenantDataSourceService;
import eterea.tenant.service.configuration.TenantInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TenantInterceptorTest {

    private MockMvc mockMvc;
    private TenantInterceptor tenantInterceptor;

    @RestController
    static class DummyTenantController {
        @GetMapping("/api/tenant/test")
        public String test() {
            return "ok";
        }
    }

    @BeforeEach
    public void setUp() {
        TenantDataSourceService tenantDataSourceService = Mockito.mock(TenantDataSourceService.class);
        tenantInterceptor = new TenantInterceptor(tenantDataSourceService);
        DummyTenantController controller = new DummyTenantController();
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addInterceptors(tenantInterceptor)
                .build();
    }

    @Test
    public void givenTenantRequest_whenHeaderMissing_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/tenant/test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenTenantRequest_whenHeaderPresent_thenReturnsOk() throws Exception {
        mockMvc.perform(get("/api/tenant/test")
                        .header("X-TENANT-ID", "tenant-123"))
                .andExpect(status().isOk());
    }
}
