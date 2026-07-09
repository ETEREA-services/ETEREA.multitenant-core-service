package eterea.tenant.service.unificado.hexagonal.negocioUnificado.application.usecases;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.in.FindAllNegocioUnificadoUseCase;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.out.NegocioUnificadoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllNegocioUnificadoUseCaseImpl implements FindAllNegocioUnificadoUseCase {

    private final NegocioUnificadoRepository repository;

    public FindAllNegocioUnificadoUseCaseImpl(NegocioUnificadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<NegocioUnificado> findAll() {
        return repository.findAll();
    }

}
