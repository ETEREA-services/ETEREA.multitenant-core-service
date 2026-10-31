package eterea.tenant.service.modules.stock.legacy.service;

import eterea.tenant.service.modules.stock.legacy.CentroStock;
import eterea.tenant.service.modules.stock.legacy.CentroStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroStockService {

    private final CentroStockRepository repository;

    public CentroStockService(CentroStockRepository repository) {
        this.repository = repository;
    }

    public List<CentroStock> findAll() {
        return repository.findAll();
    }

}
