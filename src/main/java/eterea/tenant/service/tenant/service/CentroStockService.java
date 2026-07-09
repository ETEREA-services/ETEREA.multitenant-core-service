package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.kotlin.model.CentroStock;
import eterea.tenant.service.tenant.kotlin.repository.CentroStockRepository;
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
