package eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.domain.ports.in;

import eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.domain.model.Empresa;

import java.util.Optional;

public interface GetLastEmpresaUseCase {

    Optional<Empresa> findLast();

}
