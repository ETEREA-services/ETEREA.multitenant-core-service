package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioValidator;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.FindEjercicioByIdUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.springframework.stereotype.Component;

@Component
public class FindEjercicioByIdUseCaseImpl implements FindEjercicioByIdUseCase {

    private final EjercicioRepository repository;

    public FindEjercicioByIdUseCaseImpl(EjercicioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ejercicio findById(Long ejercicioId) {
        EjercicioValidator.validarId(ejercicioId);
        return repository.findById(ejercicioId)
                .orElseThrow(() -> new EjercicioInvalidoException(
                        "EJERCICIO_NO_EXISTE",
                        "El ejercicio " + ejercicioId + " no existe."));
    }

}
