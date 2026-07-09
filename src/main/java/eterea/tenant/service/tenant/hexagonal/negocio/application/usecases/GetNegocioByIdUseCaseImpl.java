package eterea.tenant.service.tenant.hexagonal.negocio.application.usecases;

import eterea.tenant.service.tenant.hexagonal.negocio.application.exception.NegocioException;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.in.GetNegocioByIdUseCase;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.out.NegocioRepository;
import org.springframework.stereotype.Component;

@Component
public class GetNegocioByIdUseCaseImpl implements GetNegocioByIdUseCase {

    private final NegocioRepository repository;

    public GetNegocioByIdUseCaseImpl(NegocioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Negocio getNegocioById(Integer negocioId) {
        return repository.findByNegocioId(negocioId)
                .orElseThrow(() -> new NegocioException(negocioId));
    }

}
