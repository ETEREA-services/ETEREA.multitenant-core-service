package eterea.tenant.service.configuration;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
    private static final ThreadLocal<String> CURRENT_DATABASE = new ThreadLocal<>();

    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static String getCurrentDatabaseName() {
        return CURRENT_DATABASE.get();
    }

    public static void setCurrentDatabaseName(String databaseName) {
        CURRENT_DATABASE.set(databaseName);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
        CURRENT_DATABASE.remove();
    }
}
