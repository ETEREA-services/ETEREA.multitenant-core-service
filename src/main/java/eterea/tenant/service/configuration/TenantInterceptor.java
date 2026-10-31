package eterea.tenant.service.configuration;

import eterea.tenant.service.modules.platform.legacy.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_PACKAGE = "eterea.tenant.service.modules";
    private static final String TENANT_PATH_PREFIX = "/api/tenant/";
    private static final String TENANT_ATTRIBUTE = TenantInterceptor.class.getName() + ".tenant";

    private final TenantDataSourceService tenantDataSourceService;
    private final TenantAccessService tenantAccessService;

    public TenantInterceptor(
            TenantDataSourceService tenantDataSourceService,
            TenantAccessService tenantAccessService) {
        this.tenantDataSourceService = tenantDataSourceService;
        this.tenantAccessService = tenantAccessService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!esRequestDeTenant(request, handler)) {
            return true;
        }

        String tenantId = request.getHeader("X-TENANT-ID");

        if (tenantId == null || tenantId.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Required header missing: X-TENANT-ID");
        }

        validarAcceso(tenantId);

        try {
            tenantDataSourceService.ensureTenantDataSource(tenantId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        request.setAttribute(TENANT_ATTRIBUTE, tenantId);
        TenantContext.setCurrentTenant(tenantId);
        tenantDataSourceService.getDatabaseName(tenantId)
                .ifPresent(TenantContext::setCurrentDatabaseName);
        return true;
    }

    private boolean esRequestDeTenant(HttpServletRequest request, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return false;
        }
        String packageName = handlerMethod.getBeanType().getPackageName();
        return packageName.startsWith(TENANT_PACKAGE)
                || request.getRequestURI().startsWith(TENANT_PATH_PREFIX);
    }

    private void validarAcceso(String tenantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión requerida");
        }

        boolean administrador = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ADMIN".equals(authority.getAuthority()));
        if (administrador) {
            return;
        }

        UUID usuarioId;
        try {
            usuarioId = UUID.fromString(authentication.getName());
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario inválido");
        }

        if (!tenantAccessService.puedeAcceder(usuarioId, tenantId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No tenés acceso a esta empresa o negocio");
        }
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        Object tenantId = request.getAttribute(TENANT_ATTRIBUTE);
        if (tenantId instanceof String id) {
            tenantDataSourceService.releaseTenant(id);
            request.removeAttribute(TENANT_ATTRIBUTE);
        }
        TenantContext.clear();
    }

}
