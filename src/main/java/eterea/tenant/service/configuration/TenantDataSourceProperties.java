package eterea.tenant.service.configuration;

import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuración de los pools de conexión que se crean por cada tenant.
 *
 * <p>Los valores por defecto están pensados para una instancia única que atiende
 * muchas bases: pools chicos, sin conexiones ociosas y con evicción de los
 * tenants que dejaron de usarse.</p>
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.tenant.datasource")
public class TenantDataSourceProperties {

    /** Cantidad máxima de conexiones por tenant. */
    private int maximumPoolSize = 8;

    /** Conexiones que se mantienen abiertas aunque el tenant esté inactivo. */
    private int minimumIdle = 1;

    /** Tiempo tras el cual se cierran las conexiones por encima de minimumIdle. */
    private Duration idleTimeout = Duration.ofSeconds(60);

    /** Vida máxima de una conexión física. */
    private Duration maxLifetime = Duration.ofMinutes(20);

    /** Tiempo máximo de espera para obtener una conexión. */
    private Duration connectionTimeout = Duration.ofSeconds(5);

    /** Tiempo máximo para validar una conexión. */
    private Duration validationTimeout = Duration.ofSeconds(3);

    /** Inactividad a partir de la cual se cierra el pool completo del tenant. */
    private Duration evictionAfter = Duration.ofMinutes(15);

    /** Cantidad máxima de pools vivos en la instancia. */
    private int maxLivePools = 30;

    /** Cada cuánto se revisan los pools inactivos. */
    private Duration evictionInterval = Duration.ofMinutes(1);

}
