package eterea.tenant.service.unificado.hexagonal.negocioUnificado.application.usecases;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.application.exception.NegocioUnificadoException;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.in.FindNegocioUnificadoByNegocioIdUseCase;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.out.NegocioUnificadoRepository;
import org.springframework.stereotype.Component;

@Component
public class FindNegocioUnificadoByNegocioIdUseCaseImpl implements FindNegocioUnificadoByNegocioIdUseCase {

    private final NegocioUnificadoRepository repository;

    public FindNegocioUnificadoByNegocioIdUseCaseImpl(NegocioUnificadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public NegocioUnificado findByNegocioId(Integer negocioId) {
        return repository.findByNegocioId(negocioId)
                .orElseThrow(() -> new NegocioUnificadoException(negocioId));
    }

}
