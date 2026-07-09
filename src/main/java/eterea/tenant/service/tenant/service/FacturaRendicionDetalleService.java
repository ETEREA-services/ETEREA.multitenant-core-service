package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.kotlin.model.FacturaRendicionDetalle;
import eterea.tenant.service.tenant.kotlin.repository.FacturaRendicionDetalleRepository;
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
