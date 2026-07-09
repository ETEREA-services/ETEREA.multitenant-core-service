package eterea.tenant.service.tenant.service.view;

import eterea.tenant.service.tenant.kotlin.model.view.AsientoView;
import eterea.tenant.service.tenant.repository.view.IAsientoViewRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsientoViewService {

    private final IAsientoViewRepository repository;

    public AsientoViewService(IAsientoViewRepository repository) {
        this.repository = repository;
    }

    public List<AsientoView> findAllDifferences(OffsetDateTime desde, OffsetDateTime hasta) {
        return repository.findAllByFechaBetween(desde, hasta).stream().filter(asiento -> asiento.getDebe().compareTo(asiento.getHaber()) != 0).collect(Collectors.toList());
    }

    public List<AsientoView> findAllByNegocioIdAndFecha(Integer negocioId, OffsetDateTime fecha) {
        return repository.findAllByNegocioIdAndFecha(negocioId, fecha);
    }

}
