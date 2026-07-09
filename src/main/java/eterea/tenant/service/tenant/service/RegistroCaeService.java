package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.kotlin.exception.RegistroCaeException;
import eterea.tenant.service.tenant.kotlin.repository.RegistroCaeRepository;
import eterea.tenant.service.tenant.model.RegistroCae;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistroCaeService {

    private final RegistroCaeRepository repository;

    public RegistroCae findByUnique(Integer comprobanteId, Integer puntoVenta, Long numeroComprobante) {
        return Objects.requireNonNull(repository.findByComprobanteIdAndPuntoVentaAndNumeroComprobante(comprobanteId, puntoVenta, numeroComprobante)).orElseThrow(() -> new RegistroCaeException(comprobanteId, puntoVenta, numeroComprobante));
    }

    @Transactional
    public RegistroCae add(RegistroCae registroCae) {
        return repository.save(registroCae);
    }

}
