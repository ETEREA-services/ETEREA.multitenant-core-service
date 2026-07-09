package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.kotlin.exception.ParametroException;
import eterea.tenant.service.tenant.kotlin.model.Parametro;
import eterea.tenant.service.tenant.kotlin.repository.ParametroRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ParametroService {

    private final ParametroRepository repository;

    public ParametroService(ParametroRepository repository) {
        this.repository = repository;
    }

    public Parametro findTop() {
        return Objects.requireNonNull(repository.findTopByOrderByParametroIdDesc()).orElseThrow(ParametroException::new);
    }

}
