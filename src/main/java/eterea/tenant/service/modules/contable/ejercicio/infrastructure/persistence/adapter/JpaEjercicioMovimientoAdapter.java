package eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioMovimientoPort;
import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class JpaEjercicioMovimientoAdapter implements EjercicioMovimientoPort {

    private static final ZoneOffset ZONA = ZoneOffset.UTC;

    private final CuentaMovimientoRepository repository;

    @Override
    public boolean existeMovimientoEnEjercicio(Long ejercicioId) {
        if (ejercicioId == null || ejercicioId <= 0 || ejercicioId > Integer.MAX_VALUE) {
            return false;
        }
        return repository.existsByEjercicioId(ejercicioId.intValue());
    }

    @Override
    public boolean existeMovimientoEnRango(LocalDate fechaDesde, LocalDate fechaHasta) {
        if (fechaDesde == null || fechaHasta == null) {
            return false;
        }
        OffsetDateTime desde = fechaDesde.atStartOfDay().atOffset(ZONA);
        OffsetDateTime hasta = fechaHasta.plusDays(1).atStartOfDay().atOffset(ZONA);
        return repository.existsByFechaGreaterThanEqualAndFechaLessThan(desde, hasta);
    }

}
