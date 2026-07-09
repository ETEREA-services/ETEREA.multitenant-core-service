package eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;

import java.util.List;

public interface GetAllNegociosWithIpAddressUseCase {

    List<Negocio> getAllNegociosWithIpAddress(Integer negocioId);

}
