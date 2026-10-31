package eterea.tenant.service.configuration;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Resuelve y provisiona la conexión de cada tenant.
 *
 * <p>La configuración de la base sale de la tabla {@code negocio} de la base
 * maestra. El ciclo de vida (creación, uso y evicción) vive en
 * {@link TenantDataSourceRegistry}.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TenantDataSourceService {

    private final NegocioBrainRepository negocioBrainRepository;
    private final TenantDataSourceRegistry registry;
    private final TenantDataSourceProperties properties;

    public Optional<String> getDatabaseName(String tenantId) {
        return registry.databaseName(tenantId);
    }

    /**
     * Garantiza que el pool del tenant exista y lo marca en uso.
     *
     * <p>El request que llama a este método debe liberar el pool con
     * {@link #releaseTenant(String)} al terminar.</p>
     */
    public void ensureTenantDataSource(String tenantId) {
        if (registry.tryAcquire(tenantId)) {
            return;
        }

        synchronized (registry.lockFor(tenantId)) {
            if (registry.tryAcquire(tenantId)) {
                return;
            }

            NegocioBrain negocio = buscarNegocio(tenantId);
            HikariDataSource dataSource = crearDataSource(negocio);
            registry.add(tenantId, negocio.getDatabaseName(), dataSource);
            if (!registry.tryAcquire(tenantId)) {
                throw new IllegalStateException(
                        "No se pudo adquirir el pool del tenant " + tenantId);
            }
        }
    }

    public void releaseTenant(String tenantId) {
        registry.release(tenantId);
    }

    private NegocioBrain buscarNegocio(String tenantId) {
        // La consulta va a la base maestra: hay que limpiar el contexto para no
        // rutear a una base de tenant que todavía no existe.
        String previousTenant = TenantContext.getCurrentTenant();
        String previousDatabase = TenantContext.getCurrentDatabaseName();
        TenantContext.clear();
        try {
            return negocioBrainRepository.findByTenantId(tenantId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No tenant configuration found in negocio for tenantId: " + tenantId));
        } finally {
            if (previousTenant != null) {
                TenantContext.setCurrentTenant(previousTenant);
                if (previousDatabase != null) {
                    TenantContext.setCurrentDatabaseName(previousDatabase);
                }
            }
        }
    }

    private HikariDataSource crearDataSource(NegocioBrain negocio) {
        validarConexion(negocio);

        String ip = negocio.getDatabaseIp();
        String port = StringUtils.hasText(negocio.getDatabasePort())
                ? negocio.getDatabasePort()
                : "3306";
        if (!ip.contains(":")) {
            ip = ip + ":" + port;
        }
        String url = "jdbc:mysql://" + ip + "/" + negocio.getDatabaseName()
                + "?useSSL=false&serverTimezone=UTC&tcpKeepAlive=true";

        HikariConfig config = new HikariConfig();
        config.setPoolName("tenant-" + negocio.getNegocioId());
        config.setJdbcUrl(url);
        config.setUsername(negocio.getDatabaseUsername());
        config.setPassword(negocio.getDatabasePassword());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(properties.getMaximumPoolSize());
        config.setMinimumIdle(properties.getMinimumIdle());
        config.setIdleTimeout(properties.getIdleTimeout().toMillis());
        config.setMaxLifetime(properties.getMaxLifetime().toMillis());
        config.setConnectionTimeout(properties.getConnectionTimeout().toMillis());
        config.setValidationTimeout(properties.getValidationTimeout().toMillis());

        try {
            return new HikariDataSource(config);
        } catch (HikariPool.PoolInitializationException exception) {
            log.error("No se pudo conectar a la base del negocio {}: {}",
                    negocio.getNegocioId(), exception.getMessage());
            throw new IllegalStateException(
                    "No se pudo conectar a la base del negocio " + negocio.getNegocioId()
                            + ". Verificá IP, puerto, base, usuario y password.",
                    exception);
        }
    }

    private void validarConexion(NegocioBrain negocio) {
        List<String> faltantes = new ArrayList<>();
        if (!StringUtils.hasText(negocio.getDatabaseIp())) {
            faltantes.add("databaseIp");
        }
        if (!StringUtils.hasText(negocio.getDatabaseName())) {
            faltantes.add("databaseName");
        }
        if (!StringUtils.hasText(negocio.getDatabaseUsername())) {
            faltantes.add("databaseUsername");
        }
        if (!StringUtils.hasText(negocio.getDatabasePassword())) {
            faltantes.add("databasePassword");
        }
        if (!faltantes.isEmpty()) {
            throw new IllegalArgumentException(
                    "El negocio " + negocio.getNegocioId()
                            + " no tiene configurada la conexión: "
                            + String.join(", ", faltantes));
        }
    }
}
