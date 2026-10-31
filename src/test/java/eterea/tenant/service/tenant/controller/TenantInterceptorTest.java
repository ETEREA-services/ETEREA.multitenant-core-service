package eterea.tenant.service.tenant.controller;

import eterea.tenant.service.configuration.TenantAccessService;
import eterea.tenant.service.configuration.TenantDataSourceService;
import eterea.tenant.service.configuration.TenantInterceptor;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TenantInterceptorTest {

    private static final String TENANT_ID = "tenant-123";
    private static final UUID USUARIO_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    private MockMvc mockMvc;
    private TenantAccessService tenantAccessService;

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
        tenantAccessService = Mockito.mock(TenantAccessService.class);
        TenantInterceptor tenantInterceptor = new TenantInterceptor(
                tenantDataSourceService, tenantAccessService);
        DummyTenantController controller = new DummyTenantController();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addInterceptors(tenantInterceptor)
                .build();
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void givenTenantRequest_whenHeaderMissing_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/tenant/test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenTenantRequest_whenHeaderPresentAndAdmin_thenReturnsOk() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        USUARIO_ID.toString(), null,
                        List.of(new SimpleGrantedAuthority("ADMIN"))));

        mockMvc.perform(get("/api/tenant/test").header("X-TENANT-ID", TENANT_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void givenTenantRequest_whenUserHasNoAccess_thenReturnsForbidden() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(USUARIO_ID.toString(), null, List.of()));
        Mockito.when(tenantAccessService.puedeAcceder(USUARIO_ID, TENANT_ID)).thenReturn(false);

        mockMvc.perform(get("/api/tenant/test").header("X-TENANT-ID", TENANT_ID))
                .andExpect(status().isForbidden());
    }
}
