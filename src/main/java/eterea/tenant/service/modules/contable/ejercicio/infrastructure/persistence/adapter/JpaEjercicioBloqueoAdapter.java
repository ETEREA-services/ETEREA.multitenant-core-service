package eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioBloqueoPort;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class JpaEjercicioBloqueoAdapter implements EjercicioBloqueoPort {

    private final EjercicioRepository repository;

    @Override
    public boolean estaBloqueado(LocalDate fecha) {
        if (fecha == null) {
            return false;
        }
        return repository.findByFecha(fecha)
                .map(Ejercicio::isBloqueado)
                .orElse(false);
    }

}
