package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.exception.PosicionIvaException;
import eterea.tenant.service.tenant.model.PosicionIva;
import eterea.tenant.service.tenant.repository.PosicionIvaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PosicionIvaService {

    private final PosicionIvaRepository repository;

    public PosicionIvaService(PosicionIvaRepository repository) {
        this.repository = repository;
    }

    public PosicionIva findByPosicionId(Integer posicionId) {
        log.debug("Processing PosicionIvaService.findByPosicionId({})", posicionId);
        return repository.findByPosicionId(posicionId).orElseThrow(() -> new PosicionIvaException(posicionId));
    }

}
