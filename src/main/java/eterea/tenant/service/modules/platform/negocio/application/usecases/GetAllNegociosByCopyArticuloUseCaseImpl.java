package eterea.tenant.service.modules.platform.negocio.application.usecases;

import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
import eterea.tenant.service.modules.platform.negocio.domain.ports.in.GetAllNegociosByCopyArticuloUseCase;
import eterea.tenant.service.modules.platform.negocio.domain.ports.out.NegocioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllNegociosByCopyArticuloUseCaseImpl implements GetAllNegociosByCopyArticuloUseCase {

    private final NegocioRepository repository;

    public GetAllNegociosByCopyArticuloUseCaseImpl(NegocioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Negocio> getAllNegociosByCopyArticulo(Byte copyArticulo) {
        return repository.findAllByCopyArticulo(copyArticulo);
    }

}
