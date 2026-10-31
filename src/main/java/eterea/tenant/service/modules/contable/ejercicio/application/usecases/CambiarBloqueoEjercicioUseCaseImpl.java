package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioValidator;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.CambiarBloqueoEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CambiarBloqueoEjercicioUseCaseImpl implements CambiarBloqueoEjercicioUseCase {

    private final EjercicioRepository repository;

    public CambiarBloqueoEjercicioUseCaseImpl(EjercicioRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Ejercicio cambiarBloqueo(Long ejercicioId, boolean bloqueado) {
        EjercicioValidator.validarId(ejercicioId);
        Ejercicio ejercicio = repository.findById(ejercicioId)
                .orElseThrow(() -> new EjercicioInvalidoException(
                        "EJERCICIO_NO_EXISTE",
                        "El ejercicio " + ejercicioId + " no existe."));

        if (ejercicio.isBloqueado() == bloqueado) {
            return ejercicio;
        }

        ejercicio.setBloqueado(bloqueado);
        return repository.save(ejercicio);
    }

}
