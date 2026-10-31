package eterea.tenant.service.modules.platform.legacy.service;

import eterea.tenant.service.modules.platform.legacy.exception.PosicionIvaException;
import eterea.tenant.service.modules.platform.legacy.PosicionIva;
import eterea.tenant.service.modules.platform.legacy.repository.PosicionIvaRepository;
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
