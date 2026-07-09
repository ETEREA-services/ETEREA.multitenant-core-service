package eterea.tenant.service.tenant.hexagonal.comprobante.application.usecases;

import eterea.tenant.service.tenant.hexagonal.comprobante.application.exception.ComprobanteException;
import eterea.tenant.service.tenant.hexagonal.comprobante.domain.model.Comprobante;
import eterea.tenant.service.tenant.hexagonal.comprobante.domain.ports.in.FindAllComprobantesDisponiblesUseCase;
import eterea.tenant.service.tenant.hexagonal.comprobante.domain.ports.out.ComprobanteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FindAllComprobantesDisponiblesUseCaseImpl implements FindAllComprobantesDisponiblesUseCase {

    private final ComprobanteRepository repository;

    public FindAllComprobantesDisponiblesUseCaseImpl(ComprobanteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Integer> findAllDisponibles() {
        Comprobante firstComprobante = repository.findFirstByOrderByComprobanteId()
                .orElseThrow(ComprobanteException::new);
        Comprobante lastComprobante = repository.findFirstByOrderByComprobanteIdDesc()
                .orElseThrow(ComprobanteException::new);

        Map<Integer, Comprobante> usados = repository.findAll().stream()
                .collect(Collectors.toMap(Comprobante::getComprobanteId, comprobante -> comprobante));
        
        List<Integer> disponibles = new ArrayList<>();
        
        for (Integer comprobanteId = firstComprobante.getComprobanteId(); comprobanteId <= lastComprobante.getComprobanteId(); comprobanteId++) {
            if (!usados.containsKey(comprobanteId)) {
                disponibles.add(comprobanteId);
            }
        }
        return disponibles;
    }
}
