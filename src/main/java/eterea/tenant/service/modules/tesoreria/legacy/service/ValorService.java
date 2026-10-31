package eterea.tenant.service.modules.tesoreria.legacy.service;

import eterea.tenant.service.modules.tesoreria.legacy.ValorException;
import eterea.tenant.service.modules.tesoreria.legacy.Valor;
import eterea.tenant.service.modules.tesoreria.legacy.ValorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ValorService {

    private final ValorRepository repository;

    public ValorService(ValorRepository repository) {
        this.repository = repository;
    }

    public List<Valor> findAll() {
        return repository.findAll();
    }

    public Valor findByValorId(Integer valorId) {
        return Objects.requireNonNull(repository.findByValorId(valorId)).orElseThrow(() -> new ValorException(valorId));
    }

}
