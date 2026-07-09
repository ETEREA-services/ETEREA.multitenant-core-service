package eterea.tenant.service.tenant.hexagonal.empresa.domain.ports.out;

import eterea.tenant.service.tenant.hexagonal.empresa.domain.model.Empresa;

import java.util.Optional;

public interface EmpresaRepository {

    Optional<Empresa> findLast();

    void save(Empresa empresa);

}
