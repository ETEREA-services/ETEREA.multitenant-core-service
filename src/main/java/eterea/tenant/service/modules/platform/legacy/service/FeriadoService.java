package eterea.tenant.service.modules.platform.legacy.service;

import eterea.tenant.service.modules.platform.legacy.FeriadoException;
import eterea.tenant.service.modules.platform.legacy.Feriado;
import eterea.tenant.service.modules.platform.legacy.FeriadoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class FeriadoService {

    private final FeriadoRepository repository;

    public FeriadoService(FeriadoRepository repository) {
        this.repository = repository;
    }

    public List<Feriado> findAll() {
        return repository.findAll();
    }

    public Feriado findByFecha(OffsetDateTime fecha) {
        return Objects.requireNonNull(repository.findByFecha(fecha)).orElseThrow(() -> new FeriadoException(fecha));
    }

    public Feriado save(Feriado feriado) {
        return repository.save(feriado);
    }

    public void delete(OffsetDateTime fecha) {
        Feriado feriado = Objects.requireNonNull(repository.findByFecha(fecha)).orElseThrow(() -> new FeriadoException(fecha));
        assert feriado != null;
        repository.delete(feriado);
    }

}
