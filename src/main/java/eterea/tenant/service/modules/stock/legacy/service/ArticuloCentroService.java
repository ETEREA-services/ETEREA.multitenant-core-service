package eterea.tenant.service.modules.stock.legacy.service;

import eterea.tenant.service.modules.stock.legacy.ArticuloCentroException;
import eterea.tenant.service.modules.stock.legacy.ArticuloCentro;
import eterea.tenant.service.modules.stock.legacy.ArticuloCentroRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ArticuloCentroService {

    private final ArticuloCentroRepository repository;

    public ArticuloCentroService(ArticuloCentroRepository repository) {
        this.repository = repository;
    }

    public List<ArticuloCentro> findAllByArticulos(Integer centroStockId, List<String> articuloIds) {
        return repository.findAllByCentroStockIdAndArticuloIdIn(centroStockId, articuloIds);
    }

    public ArticuloCentro findByUnique(Integer centroStockId, String articuloId) {
        return repository.findByCentroStockIdAndArticuloId(centroStockId, articuloId)
                .orElseThrow(() -> new ArticuloCentroException(centroStockId, articuloId));
    }

    public ArticuloCentro save(ArticuloCentro articuloCentro) {
        return repository.save(articuloCentro);
    }

    public List<ArticuloCentro> saveAll(List<ArticuloCentro> articuloCentros) {
        return repository.saveAll(articuloCentros);
    }
}
