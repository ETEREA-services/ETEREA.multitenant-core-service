package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioValidator;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.CreateEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateEjercicioUseCaseImpl implements CreateEjercicioUseCase {

    private final EjercicioRepository repository;

    public CreateEjercicioUseCaseImpl(EjercicioRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Ejercicio create(Ejercicio nuevo) {
        String nombre = EjercicioValidator.normalizarNombre(nuevo.getNombre());
        EjercicioValidator.validarFechas(nuevo.getFechaInicio(), nuevo.getFechaFin());

        if (repository.existsSolapamiento(nuevo.getFechaInicio(), nuevo.getFechaFin())) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_SOLAPADO",
                    "El ejercicio se superpone con otro ejercicio existente.");
        }

        Ejercicio ejercicio = Ejercicio.builder()
                .nombre(nombre)
                .fechaInicio(nuevo.getFechaInicio())
                .fechaFin(nuevo.getFechaFin())
                .bloqueado(false)
                .build();

        return repository.save(ejercicio);
    }

}
