package eterea.tenant.service.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private final TenantDataSourceService tenantDataSourceService;

    public TenantInterceptor(TenantDataSourceService tenantDataSourceService) {
        this.tenantDataSourceService = tenantDataSourceService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> controllerClass = handlerMethod.getBeanType();
            String packageName = controllerClass.getPackageName();

            // Only intercept endpoints inside the tenant package
            if (packageName.startsWith("eterea.tenant.service.tenant")) {
                String tenantId = request.getHeader("X-TENANT-ID");

                if (tenantId == null || tenantId.trim().isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required header missing: X-TENANT-ID");
                }

                try {
                    tenantDataSourceService.ensureTenantDataSource(tenantId);
                    TenantContext.setCurrentTenant(tenantId);
                } catch (IllegalArgumentException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }
}
