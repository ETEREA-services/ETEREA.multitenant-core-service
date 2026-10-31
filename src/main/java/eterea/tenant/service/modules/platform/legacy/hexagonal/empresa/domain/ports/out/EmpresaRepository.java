package eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.domain.ports.out;

import eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.domain.model.Empresa;

import java.util.Optional;

public interface EmpresaRepository {

    Optional<Empresa> findLast();

    void save(Empresa empresa);

}
