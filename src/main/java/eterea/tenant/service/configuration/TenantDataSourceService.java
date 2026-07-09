package eterea.tenant.service.configuration;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.out.NegocioUnificadoRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Optional;

@Service
public class TenantDataSourceService {

    private final NegocioUnificadoRepository negocioUnificadoRepository;
    private final TenantRoutingDataSource routingDataSource;
    private final DataSourceProperties masterDataSourceProperties;

    public TenantDataSourceService(NegocioUnificadoRepository negocioUnificadoRepository,
                                   TenantRoutingDataSource routingDataSource,
                                   DataSourceProperties masterDataSourceProperties) {
        this.negocioUnificadoRepository = negocioUnificadoRepository;
        this.routingDataSource = routingDataSource;
        this.masterDataSourceProperties = masterDataSourceProperties;
    }

    public void ensureTenantDataSource(String tenantId) {
        if (routingDataSource.hasTenantDataSource(tenantId)) {
            return;
        }

        // Query the master database for tenant connection details.
        // We must ensure that TenantContext is cleared during this lookup 
        // to avoid routing to a tenant database before it is created.
        String previousTenant = TenantContext.getCurrentTenant();
        TenantContext.clear();
        try {
            Optional<NegocioUnificado> negocioOpt = negocioUnificadoRepository.findByTenantId(tenantId);
            if (negocioOpt.isEmpty()) {
                throw new IllegalArgumentException("No tenant configuration found in negocio for tenantId: " + tenantId);
            }

            NegocioUnificado negocio = negocioOpt.get();
            DataSource tenantDs = createDataSource(negocio);
            routingDataSource.addTenantDataSource(tenantId, tenantDs);
        } finally {
            if (previousTenant != null) {
                TenantContext.setCurrentTenant(previousTenant);
            }
        }
    }

    private DataSource createDataSource(NegocioUnificado negocio) {
        String ip = negocio.getDatabaseIp();
        String port = negocio.getDatabasePort();
        if (ip != null && !ip.contains(":")) {
            ip = ip + ":" + (port != null ? port : "3306");
        }
        String url = "jdbc:mysql://" + ip + "/" + negocio.getDatabaseName() + "?useSSL=false&serverTimezone=UTC&tcpKeepAlive=true";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(negocio.getDatabaseUsername());
        config.setPassword(masterDataSourceProperties.getPassword());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        
        // 30 connections per tenant datasource as requested by user
        config.setMaximumPoolSize(30);
        config.setMaxLifetime(1200000); // 20 minutes
        config.setIdleTimeout(600000);   // 10 minutes
        config.setConnectionTimeout(30000);
        config.setValidationTimeout(3000);
        
        return new HikariDataSource(config);
    }
}
