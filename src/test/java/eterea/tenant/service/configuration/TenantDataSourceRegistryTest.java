package eterea.tenant.service.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TenantDataSourceRegistryTest {

    private TenantDataSourceProperties properties;
    private TenantRoutingDataSource routingDataSource;
    private TenantDataSourceRegistry registry;

    @BeforeEach
    void setUp() {
        properties = new TenantDataSourceProperties();
        properties.setMaxLivePools(10);
        properties.setEvictionAfter(Duration.ofMinutes(15));
        routingDataSource = new TenantRoutingDataSource();
        registry = new TenantDataSourceRegistry(
                routingDataSource, properties, new SimpleMeterRegistry());
        registry.registerMetrics();
    }

    @AfterEach
    void tearDown() {
        registry.closeAll();
        TenantContext.clear();
    }

    @Test
    void addRegistersTenantAndDatabaseName() {
        HikariDataSource dataSource = mock(HikariDataSource.class);

        registry.add("tenant-a", "negocio_a", dataSource);

        assertThat(registry.contains("tenant-a")).isTrue();
        assertThat(registry.databaseName("tenant-a")).contains("negocio_a");
        assertThat(registry.activePools()).isEqualTo(1);
        assertThat(registry.createdPoolCount()).isEqualTo(1);
    }

    @Test
    void evictIfUnusedKeepsPoolWhileARequestIsInFlight() {
        HikariDataSource dataSource = mock(HikariDataSource.class);
        registry.add("tenant-a", "negocio_a", dataSource);
        assertThat(registry.tryAcquire("tenant-a")).isTrue();

        assertThat(registry.evictIfUnused("tenant-a")).isFalse();

        registry.release("tenant-a");
        verify(dataSource, never()).close();
    }

    @Test
    void evictIfUnusedClosesPoolWhenNoRequestIsInFlight() {
        HikariDataSource dataSource = mock(HikariDataSource.class);
        registry.add("tenant-a", "negocio_a", dataSource);

        assertThat(registry.evictIfUnused("tenant-a")).isTrue();

        assertThat(registry.contains("tenant-a")).isFalse();
        assertThat(routingDataSource.hasTenantDataSource("tenant-a")).isFalse();
        assertThat(registry.evictedPoolCount()).isEqualTo(1);
        verify(dataSource).close();
    }

    @Test
    void evictIdlePoolsClosesTenantsPastTheIdleThreshold() {
        properties.setEvictionAfter(Duration.ZERO);
        HikariDataSource dataSource = mock(HikariDataSource.class);
        registry.add("tenant-a", "negocio_a", dataSource);

        registry.evictIdlePools();

        assertThat(registry.contains("tenant-a")).isFalse();
        verify(dataSource).close();
    }

    @Test
    void evictIdlePoolsKeepsPoolsBelowTheIdleThreshold() {
        HikariDataSource dataSource = mock(HikariDataSource.class);
        registry.add("tenant-a", "negocio_a", dataSource);

        registry.evictIdlePools();

        assertThat(registry.contains("tenant-a")).isTrue();
        verify(dataSource, never()).close();
    }

    @Test
    void evictsTheOldestPoolsWhenOverCapacity() {
        properties.setEvictionAfter(Duration.ofDays(1));
        properties.setMaxLivePools(2);
        registry.add("tenant-a", "negocio_a", mock(HikariDataSource.class));
        registry.add("tenant-b", "negocio_b", mock(HikariDataSource.class));
        registry.add("tenant-c", "negocio_c", mock(HikariDataSource.class));

        registry.evictIdlePools();

        assertThat(registry.activePools()).isEqualTo(2);
        assertThat(registry.contains("tenant-a")).isFalse();
        assertThat(registry.contains("tenant-b")).isTrue();
        assertThat(registry.contains("tenant-c")).isTrue();
    }

    @Test
    void tryAcquireFailsWhenThePoolDoesNotExist() {
        assertThat(registry.tryAcquire("tenant-a")).isFalse();
    }

    @Test
    void tryAcquireFailsAfterEvictionSoTheCallerCanRecreateThePool() {
        registry.add("tenant-a", "negocio_a", mock(HikariDataSource.class));
        assertThat(registry.evictIfUnused("tenant-a")).isTrue();

        assertThat(registry.tryAcquire("tenant-a")).isFalse();
    }

    @Test
    void releaseWithoutAcquireDoesNotGoNegative() {
        registry.add("tenant-a", "negocio_a", mock(HikariDataSource.class));

        registry.release("tenant-a");
        registry.release("tenant-a");

        assertThat(registry.inUseTotal()).isZero();
    }

    @Test
    void routingFailsInsteadOfFallingBackToTheMasterDatabase() {
        TenantContext.setCurrentTenant("tenant-sin-pool");

        assertThatThrownBy(() -> routingDataSource.getConnection())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("tenant-sin-pool");
    }

}
