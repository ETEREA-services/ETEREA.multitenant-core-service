package eterea.tenant.service.configuration;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Registro y ciclo de vida de los pools de conexión por tenant.
 *
 * <p>Una instancia atiende muchas bases. Para no agotar las conexiones del
 * servidor MySQL, los pools se crean bajo demanda, se contabiliza cuántos
 * requests los están usando y se cierran cuando el tenant lleva un tiempo
 * inactivo o cuando se supera {@link TenantDataSourceProperties#getMaxLivePools()}.</p>
 *
 * <p>Un pool nunca se cierra mientras tenga requests en curso: el interceptor de
 * tenant llama a {@link #acquire(String)} al entrar y a {@link #release(String)}
 * al salir.</p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TenantDataSourceRegistry {

    private final TenantRoutingDataSource routingDataSource;
    private final TenantDataSourceProperties properties;
    private final MeterRegistry meterRegistry;

    private final Map<String, ManagedTenantDataSource> pools = new ConcurrentHashMap<>();
    private final Map<String, Object> locks = new ConcurrentHashMap<>();
    private final AtomicInteger createdPools = new AtomicInteger();
    private final AtomicInteger evictedPools = new AtomicInteger();
    private final AtomicLong sequence = new AtomicLong();

    /** Lock por tenant para que dos requests no creen el mismo pool a la vez. */
    public Object lockFor(String tenantId) {
        return locks.computeIfAbsent(tenantId, key -> new Object());
    }

    public boolean contains(String tenantId) {
        return pools.containsKey(tenantId);
    }

    public Optional<String> databaseName(String tenantId) {
        return Optional.ofNullable(pools.get(tenantId))
                .map(ManagedTenantDataSource::getDatabaseName);
    }

    public void add(String tenantId, String databaseName, HikariDataSource dataSource) {
        ManagedTenantDataSource managed = new ManagedTenantDataSource(
                tenantId, databaseName, dataSource, sequence.incrementAndGet());
        ManagedTenantDataSource previous = pools.put(tenantId, managed);
        routingDataSource.addTenantDataSource(tenantId, dataSource);
        createdPools.incrementAndGet();
        if (previous != null) {
            closeQuietly(previous);
        }
        log.info("Pool de tenant creado tenant={} database={}", tenantId, databaseName);
    }

    public boolean tryAcquire(String tenantId) {
        ManagedTenantDataSource managed = pools.get(tenantId);
        if (managed == null) {
            return false;
        }
        synchronized (managed) {
            if (managed.isRemoved()) {
                return false;
            }
            managed.getInUse().incrementAndGet();
            managed.touch();
            return true;
        }
    }

    public void release(String tenantId) {
        ManagedTenantDataSource managed = pools.get(tenantId);
        if (managed == null) {
            return;
        }
        synchronized (managed) {
            managed.getInUse().updateAndGet(current -> current > 0 ? current - 1 : 0);
        }
    }

    /** Cierra el pool del tenant si no tiene requests en curso. */
    public boolean evictIfUnused(String tenantId) {
        ManagedTenantDataSource managed = pools.get(tenantId);
        if (managed == null) {
            return false;
        }
        synchronized (managed) {
            if (managed.isRemoved() || managed.getInUse().get() > 0) {
                return false;
            }
            removeAndClose(tenantId, managed);
            return true;
        }
    }

    /**
     * Cierra los pools que llevan más de {@code eviction-after} sin uso y, si
     * quedan más de {@code max-live-pools}, libera los más antiguos.
     */
    @Scheduled(fixedDelayString = "${app.tenant.datasource.eviction-interval:1m}")
    public void evictIdlePools() {
        long now = System.currentTimeMillis();
        long threshold = properties.getEvictionAfter().toMillis();
        for (String tenantId : List.copyOf(pools.keySet())) {
            ManagedTenantDataSource managed = pools.get(tenantId);
            if (managed != null && now - managed.getLastAccess() >= threshold) {
                evictIfUnused(tenantId);
            }
        }
        evictOverCapacity();
    }

    private void evictOverCapacity() {
        int excess = pools.size() - properties.getMaxLivePools();
        if (excess <= 0) {
            return;
        }
        pools.entrySet().stream()
                .sorted(Comparator
                        .comparingLong((Map.Entry<String, ManagedTenantDataSource> entry)
                                -> entry.getValue().getLastAccess())
                        .thenComparingLong(entry -> entry.getValue().getSequence()))
                .map(Map.Entry::getKey)
                .limit(excess)
                .forEach(this::evictIfUnused);
    }

    private void removeAndClose(String tenantId, ManagedTenantDataSource managed) {
        managed.markRemoved();
        pools.remove(tenantId);
        routingDataSource.removeTenantDataSource(tenantId);
        closeQuietly(managed);
        evictedPools.incrementAndGet();
        log.info("Pool de tenant liberado tenant={} database={}",
                tenantId, managed.getDatabaseName());
    }

    private void closeQuietly(ManagedTenantDataSource managed) {
        try {
            managed.getDataSource().close();
        } catch (RuntimeException exception) {
            log.warn("Error cerrando el pool del tenant {}: {}",
                    managed.getTenantId(), exception.getMessage());
        }
    }

    @PreDestroy
    public void closeAll() {
        for (String tenantId : List.copyOf(pools.keySet())) {
            ManagedTenantDataSource managed = pools.get(tenantId);
            if (managed == null) {
                continue;
            }
            synchronized (managed) {
                if (!managed.isRemoved()) {
                    removeAndClose(tenantId, managed);
                }
            }
        }
    }

    @PostConstruct
    void registerMetrics() {
        Gauge.builder("eterea.tenant.datasource.pools", this, TenantDataSourceRegistry::activePools)
                .description("Pools de tenant vivos")
                .register(meterRegistry);
        Gauge.builder("eterea.tenant.datasource.requests", this, TenantDataSourceRegistry::inUseTotal)
                .description("Requests de tenant en curso")
                .register(meterRegistry);
        Gauge.builder("eterea.tenant.datasource.connections", this, TenantDataSourceRegistry::activeConnections)
                .description("Conexiones activas en los pools de tenant")
                .register(meterRegistry);
        Gauge.builder("eterea.tenant.datasource.pools.created", this, TenantDataSourceRegistry::createdPoolCount)
                .description("Pools de tenant creados desde el arranque")
                .register(meterRegistry);
        Gauge.builder("eterea.tenant.datasource.pools.evicted", this, TenantDataSourceRegistry::evictedPoolCount)
                .description("Pools de tenant liberados desde el arranque")
                .register(meterRegistry);
    }

    public int activePools() {
        return pools.size();
    }

    public int inUseTotal() {
        return pools.values().stream()
                .mapToInt(managed -> managed.getInUse().get())
                .sum();
    }

    public int activeConnections() {
        int total = 0;
        for (ManagedTenantDataSource managed : pools.values()) {
            try {
                HikariPoolMXBean pool = managed.getDataSource().getHikariPoolMXBean();
                if (pool != null) {
                    total += pool.getActiveConnections();
                }
            } catch (RuntimeException ignored) {
                // El pool puede haberse cerrado entre la lectura del mapa y la métrica.
            }
        }
        return total;
    }

    public int createdPoolCount() {
        return createdPools.get();
    }

    public int evictedPoolCount() {
        return evictedPools.get();
    }

    @Getter
    private static final class ManagedTenantDataSource {

        private final String tenantId;
        private final String databaseName;
        private final HikariDataSource dataSource;
        private final long sequence;
        private final AtomicInteger inUse = new AtomicInteger();
        private volatile long lastAccess = System.currentTimeMillis();
        private boolean removed;

        private ManagedTenantDataSource(
                String tenantId,
                String databaseName,
                HikariDataSource dataSource,
                long sequence) {
            this.tenantId = tenantId;
            this.databaseName = databaseName;
            this.dataSource = dataSource;
            this.sequence = sequence;
        }

        void touch() {
            this.lastAccess = System.currentTimeMillis();
        }

        void markRemoved() {
            this.removed = true;
        }

    }

}
