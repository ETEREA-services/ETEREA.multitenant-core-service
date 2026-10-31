package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.FindAllEjerciciosUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllEjerciciosUseCaseImpl implements FindAllEjerciciosUseCase {

    private final EjercicioRepository repository;

    public FindAllEjerciciosUseCaseImpl(EjercicioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Ejercicio> findAll() {
        return repository.findAll();
    }

}
