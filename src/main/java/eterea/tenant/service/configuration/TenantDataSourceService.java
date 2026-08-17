package eterea.tenant.service.configuration;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantDataSourceService {

    private final NegocioBrainRepository negocioBrainRepository;
    private final TenantRoutingDataSource routingDataSource;
    private final DataSourceProperties masterDataSourceProperties;

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
            Optional<NegocioBrain> negocioOpt = negocioBrainRepository.findByTenantId(tenantId);
            if (negocioOpt.isEmpty()) {
                throw new IllegalArgumentException("No tenant configuration found in negocio for tenantId: " + tenantId);
            }

            NegocioBrain negocio = negocioOpt.get();
            DataSource tenantDs = createDataSource(negocio);
            routingDataSource.addTenantDataSource(tenantId, tenantDs);
        } finally {
            if (previousTenant != null) {
                TenantContext.setCurrentTenant(previousTenant);
            }
        }
    }

    private DataSource createDataSource(NegocioBrain negocio) {
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
