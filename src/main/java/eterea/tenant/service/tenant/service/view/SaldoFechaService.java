package eterea.tenant.service.tenant.service.view;

import eterea.tenant.service.tenant.kotlin.exception.view.SaldoFechaException;
import eterea.tenant.service.tenant.kotlin.model.view.SaldoFecha;
import eterea.tenant.service.tenant.kotlin.repository.view.SaldoFechaRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class SaldoFechaService {

    private final SaldoFechaRepository repository;

    public SaldoFechaService(SaldoFechaRepository repository) {
        this.repository = repository;
    }

    public List<SaldoFecha> findAllByArticulos(Integer centroStockId, OffsetDateTime fechaMovimiento, List<String> articuloIds) {
        return repository.findAllByCentroStockIdAndFechaAndArticuloIdIn(centroStockId, fechaMovimiento, articuloIds);
    }

    public SaldoFecha findByUnique(Integer centroStockId, String articuloId, OffsetDateTime fechaMovimiento) {
        return repository.findByCentroStockIdAndArticuloIdAndFecha(centroStockId, articuloId, fechaMovimiento)
                .orElseThrow(() -> new SaldoFechaException(centroStockId, articuloId, fechaMovimiento));
    }

}
