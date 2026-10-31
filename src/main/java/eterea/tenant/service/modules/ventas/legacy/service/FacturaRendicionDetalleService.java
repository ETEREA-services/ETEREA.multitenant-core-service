package eterea.tenant.service.modules.ventas.legacy.service;

import eterea.tenant.service.modules.ventas.legacy.FacturaRendicionDetalle;
import eterea.tenant.service.modules.ventas.legacy.FacturaRendicionDetalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaRendicionDetalleService {

    private final FacturaRendicionDetalleRepository repository;

    public FacturaRendicionDetalleService(FacturaRendicionDetalleRepository repository) {
        this.repository = repository;
    }

    public List<FacturaRendicionDetalle> findAllByProveedorId(Long proveedorId) {
        return repository.findAllByProveedorId(proveedorId);
    }
}
