package eterea.tenant.service.modules.contable.legacy.service;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoFirmaException;
import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoFirma;
import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoFirmaRepository;
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
