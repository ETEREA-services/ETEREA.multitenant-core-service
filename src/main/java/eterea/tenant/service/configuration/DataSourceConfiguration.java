package eterea.tenant.service.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource masterDataSource() {
        return masterDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSource masterDataSource) {
        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        
        java.util.Map<Object, Object> targetDataSources = new java.util.HashMap<>();
        targetDataSources.put("MASTER", masterDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);
        
        // Add master data source to map
        routingDataSource.addTenantDataSource("MASTER", masterDataSource);
        
        routingDataSource.afterPropertiesSet();
        return routingDataSource;
    }
}
