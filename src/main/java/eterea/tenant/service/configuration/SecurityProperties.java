package eterea.tenant.service.configuration;

import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Configuración de seguridad y emisión de tokens. */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    /** Secreto HMAC usado por el core y el gateway. Mínimo 32 bytes. */
    private String jwtSecret;

    /** Emisor que se valida en los tokens. */
    private String issuer = "eterea.multitenant";

    /** Vida del access token. */
    private Duration accessTokenTtl = Duration.ofMinutes(15);

    /** Vida del refresh token y de su cookie. */
    private Duration refreshTokenTtl = Duration.ofDays(7);

    /** Cookie Secure (true en producción detrás de HTTPS). */
    private boolean cookieSecure = false;

    /** Intentos fallidos antes de bloquear temporalmente la cuenta. */
    private int maxFailedAttempts = 5;

    /** Duración del bloqueo temporal. */
    private Duration lockDuration = Duration.ofMinutes(15);

    /** Vigencia de una contraseña temporal generada por un blanqueo. */
    private Duration temporaryPasswordTtl = Duration.ofHours(24);

}
