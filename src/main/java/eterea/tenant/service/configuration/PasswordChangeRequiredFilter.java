package eterea.tenant.service.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Bloquea el resto de la API mientras el usuario tenga pendiente cambiar una
 * contraseña temporal.
 *
 * <p>Se ejecuta dentro de la cadena de seguridad, después de validar el JWT, y
 * sólo permite el cambio de contraseña, el logout y la consulta de la sesión.</p>
 */
public class PasswordChangeRequiredFilter extends OncePerRequestFilter {

    private static final String CLAIM = "pwd_change_required";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.getPrincipal() instanceof Jwt jwt
                && Boolean.TRUE.equals(jwt.getClaimAsBoolean(CLAIM))
                && !esPermitida(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    "{\"code\":\"PASSWORD_CHANGE_REQUIRED\","
                            + "\"message\":\"Tenés que cambiar la contraseña temporal antes de continuar.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean esPermitida(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }
        if ("PUT".equalsIgnoreCase(method)
                && path.matches("^/api/brain/core/usuario/[^/]+/password$")) {
            return true;
        }
        if ("POST".equalsIgnoreCase(method) && "/api/brain/auth/logout".equals(path)) {
            return true;
        }
        return "GET".equalsIgnoreCase(method) && "/api/brain/auth/me".equals(path);
    }
}
