package eterea.tenant.service.modules.contable.legacy.service;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoMoneda;
import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoMonedaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CuentaMovimientoMonedaService {

    private final CuentaMovimientoMonedaRepository repository;

    public CuentaMovimientoMonedaService(CuentaMovimientoMonedaRepository repository) {
        this.repository = repository;
    }

    public List<CuentaMovimientoMoneda> saveAll(List<CuentaMovimientoMoneda> cuentaMovimientoMonedas) {
        return repository.saveAll(cuentaMovimientoMonedas);
    }

    public void generateMovimientosCotizados(Integer monedaIdOrigen, Integer monedaIdDestino, OffsetDateTime fechaDesde, OffsetDateTime fechaHasta) {
        repository.insertOrUpdateMovimientosCotizados(monedaIdOrigen, monedaIdDestino, fechaDesde, fechaHasta);
    }

    @Transactional
    public int deleteOrphanedRecords(Integer monedaId, OffsetDateTime fechaDesde, OffsetDateTime fechaHasta) {
        return repository.deleteOrphanedRecords(monedaId, fechaDesde, fechaHasta);
    }
}
