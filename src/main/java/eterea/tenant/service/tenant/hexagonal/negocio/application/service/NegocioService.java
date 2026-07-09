package eterea.tenant.service.tenant.hexagonal.negocio.application.service;

import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.in.GetAllNegociosByCopyArticuloUseCase;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.in.GetAllNegociosWithIpAddressUseCase;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.in.GetNegocioByIdUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NegocioService {

    private final GetAllNegociosByCopyArticuloUseCase getAllNegociosByCopyArticuloUseCase;
    private final GetNegocioByIdUseCase getNegocioByIdUseCase;
    private final GetAllNegociosWithIpAddressUseCase getAllNegociosWithIpAddressUseCase;

    public NegocioService(GetAllNegociosByCopyArticuloUseCase getAllNegociosByCopyArticuloUseCase,
                          GetNegocioByIdUseCase getNegocioByIdUseCase,
                          GetAllNegociosWithIpAddressUseCase getAllNegociosWithIpAddressUseCase) {
        this.getAllNegociosByCopyArticuloUseCase = getAllNegociosByCopyArticuloUseCase;
        this.getNegocioByIdUseCase = getNegocioByIdUseCase;
        this.getAllNegociosWithIpAddressUseCase = getAllNegociosWithIpAddressUseCase;
    }

    public List<Negocio> getAllNegociosByCopyArticulo(Byte copyArticulo) {
        return getAllNegociosByCopyArticuloUseCase.getAllNegociosByCopyArticulo(copyArticulo);
    }

    public Negocio getNegocioById(Integer negocioId) {
        return getNegocioByIdUseCase.getNegocioById(negocioId);
    }

    public List<Negocio> getAllNegociosWithIpAddress(Integer negocioId) {
        return getAllNegociosWithIpAddressUseCase.getAllNegociosWithIpAddress(negocioId);
    }

}
