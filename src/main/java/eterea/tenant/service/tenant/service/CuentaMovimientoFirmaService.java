package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.kotlin.exception.CuentaMovimientoFirmaException;
import eterea.tenant.service.tenant.kotlin.model.CuentaMovimientoFirma;
import eterea.tenant.service.tenant.kotlin.repository.CuentaMovimientoFirmaRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;

@Service
public class CuentaMovimientoFirmaService {

    private final CuentaMovimientoFirmaRepository repository;

    public CuentaMovimientoFirmaService(CuentaMovimientoFirmaRepository repository) {
        this.repository = repository;
    }

    public CuentaMovimientoFirma findByAsiento(OffsetDateTime fechaContable, Integer ordenContable) {
        return Objects.requireNonNull(repository.findByFechaContableAndOrdenContable(fechaContable, ordenContable)).orElseThrow(() -> new CuentaMovimientoFirmaException(fechaContable, ordenContable));
    }

}
