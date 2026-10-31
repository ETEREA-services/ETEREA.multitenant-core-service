package eterea.tenant.service.modules.stock.legacy.service;

import eterea.tenant.service.modules.stock.legacy.exception.StockMovimientoException;
import eterea.tenant.service.modules.stock.legacy.StockMovimiento;
import eterea.tenant.service.modules.stock.legacy.StockMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StockMovimientoService {

    private final StockMovimientoRepository repository;

    public StockMovimiento findByStockMovimientoId(Long stockMovimientoId) {
        return Objects.requireNonNull(repository.findByStockMovimientoId(stockMovimientoId)).orElseThrow(() -> new StockMovimientoException(stockMovimientoId));
    }

    public StockMovimiento getLastByComprobanteId(Integer comprobanteId) {
        return Objects.requireNonNull(repository.findFirstByComprobanteIdOrderByStockMovimientoIdDesc(comprobanteId))
                .orElseThrow(() -> new StockMovimientoException(comprobanteId));
    }

    public StockMovimiento add(StockMovimiento stockMovimiento) {
        return repository.save(stockMovimiento);
    }

}
