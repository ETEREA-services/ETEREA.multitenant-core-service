package eterea.tenant.service.configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    private final Map<Object, DataSource> tenantDataSources = new ConcurrentHashMap<>();
    private DataSource defaultDataSource;

    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        this.defaultDataSource = (DataSource) defaultTargetDataSource;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        Object lookupKey = determineCurrentLookupKey();
        if (lookupKey == null) {
            return defaultDataSource;
        }
        DataSource tenantDataSource = tenantDataSources.get(lookupKey);
        if (tenantDataSource == null) {
            // Si hay un tenant en contexto, la conexión tiene que ser la del
            // tenant. Caer a la base maestra en silencio mezclaría datos.
            throw new IllegalStateException(
                    "No hay datasource registrado para el tenant " + lookupKey);
        }
        return tenantDataSource;
    }

    public void addTenantDataSource(String tenantId, DataSource dataSource) {
        tenantDataSources.put(tenantId, dataSource);
    }

    public void removeTenantDataSource(String tenantId) {
        tenantDataSources.remove(tenantId);
    }

    public boolean hasTenantDataSource(String tenantId) {
        return tenantDataSources.containsKey(tenantId);
    }
}
