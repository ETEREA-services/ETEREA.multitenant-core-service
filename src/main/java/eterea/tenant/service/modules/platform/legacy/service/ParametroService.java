package eterea.tenant.service.modules.platform.legacy.service;

import eterea.tenant.service.modules.platform.legacy.ParametroException;
import eterea.tenant.service.modules.platform.legacy.Parametro;
import eterea.tenant.service.modules.platform.legacy.ParametroRepository;
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
