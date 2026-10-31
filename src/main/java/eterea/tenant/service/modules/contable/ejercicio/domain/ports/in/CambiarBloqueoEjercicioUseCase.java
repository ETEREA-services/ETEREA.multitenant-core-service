package eterea.tenant.service.modules.contable.ejercicio.domain.ports.in;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;

public interface CambiarBloqueoEjercicioUseCase {
    Ejercicio cambiarBloqueo(Long ejercicioId, boolean bloqueado);
}
