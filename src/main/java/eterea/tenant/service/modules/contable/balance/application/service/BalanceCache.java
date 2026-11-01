package eterea.tenant.service.modules.contable.balance.application.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import eterea.tenant.service.modules.contable.balance.domain.model.Balance;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Caché en memoria del balance (D1-C).
 *
 * <p>La clave la arma el caso de uso con tenant, filtros y watermark. El TTL
 * corto cubre escrituras que no actualicen el watermark; el tamaño máximo evita
 * crecer sin control. No es una fuente de verdad: si se vacía, se recalcula.</p>
 */
@Component
public class BalanceCache {

    private final Cache<String, Balance> cache = Caffeine.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(Duration.ofSeconds(60))
            .build();

    public Balance get(String clave) {
        return cache.getIfPresent(clave);
    }

    public void put(String clave, Balance balance) {
        cache.put(clave, balance);
    }

    public void invalidarTodo() {
        cache.invalidateAll();
    }

}
