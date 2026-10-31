package eterea.tenant.service.modules.platform.negocio.application.usecases;

import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
import eterea.tenant.service.modules.platform.negocio.domain.ports.in.GetAllNegociosWithIpAddressUseCase;
import eterea.tenant.service.modules.platform.negocio.domain.ports.out.NegocioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllNegociosWithIpAddressUseCaseImpl implements GetAllNegociosWithIpAddressUseCase {

    private final NegocioRepository repository;

    public GetAllNegociosWithIpAddressUseCaseImpl(NegocioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Negocio> getAllNegociosWithIpAddress(Integer negocioId) {
        return repository.findAllWithIpAddress(negocioId);
    }

}
