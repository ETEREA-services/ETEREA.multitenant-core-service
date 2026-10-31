package eterea.tenant.service.modules.contable.ejercicio.domain.ports.in;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;

import java.util.List;

public interface FindAllEjerciciosUseCase {
    List<Ejercicio> findAll();
}
