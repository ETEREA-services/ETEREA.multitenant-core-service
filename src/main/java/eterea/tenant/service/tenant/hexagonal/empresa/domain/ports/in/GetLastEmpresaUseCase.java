package eterea.tenant.service.tenant.hexagonal.empresa.domain.ports.in;

import eterea.tenant.service.tenant.hexagonal.empresa.domain.model.Empresa;

import java.util.Optional;

public interface GetLastEmpresaUseCase {

    Optional<Empresa> findLast();

}
