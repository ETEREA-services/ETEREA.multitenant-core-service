package eterea.tenant.service.modules.platform.negocio.domain.ports.in;

import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;

import java.util.List;

public interface GetAllNegociosWithIpAddressUseCase {

    List<Negocio> getAllNegociosWithIpAddress(Integer negocioId);

}
