package eterea.tenant.service.modules.contable.ejercicio.domain.ports.in;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;

public interface FindEjercicioByIdUseCase {
    Ejercicio findById(Long ejercicioId);
}
