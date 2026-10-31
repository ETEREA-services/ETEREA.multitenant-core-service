package eterea.tenant.service.modules.stock.legacy.service;

import eterea.tenant.service.modules.stock.legacy.RubroListaPrecioException;
import eterea.tenant.service.modules.stock.legacy.RubroListaPrecio;
import eterea.tenant.service.modules.stock.legacy.RubroListaPrecioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RubroListaPrecioService {

    private final RubroListaPrecioRepository repository;

    public RubroListaPrecioService(RubroListaPrecioRepository repository) {
        this.repository = repository;
    }

    public List<RubroListaPrecio> findAll() {
        return repository.findAllByOrderByRubroDescripcion();
    }

    public List<RubroListaPrecio> findAllByPublicar() {
        return repository.findAllByPublicarOrderByRubroDescripcion((byte) 1);
    }

    public RubroListaPrecio findByRubroId(Long rubroId) {
        return Objects.requireNonNull(repository.findByRubroId(rubroId)).orElseThrow(() -> new RubroListaPrecioException(rubroId));
    }

    public RubroListaPrecio addOrUpdate(RubroListaPrecio rubroListaPrecio) {
        // Buscar si existe un registro con el mismo rubroId
        Optional<RubroListaPrecio> existingRubro = repository.findByRubroId(rubroListaPrecio.getRubroId());
        
        if (existingRubro.isPresent()) {
            // Actualizar el registro existente
            RubroListaPrecio existing = existingRubro.get();
            existing.setEtiqueta(rubroListaPrecio.getEtiqueta());
            existing.setPublicar(rubroListaPrecio.getPublicar());
            return repository.save(existing);
        } else {
            // Crear nuevo registro
            return repository.save(rubroListaPrecio);
        }
    }
}
