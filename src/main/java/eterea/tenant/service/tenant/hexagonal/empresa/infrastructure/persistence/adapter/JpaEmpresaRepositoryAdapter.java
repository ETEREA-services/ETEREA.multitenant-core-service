package eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.persistence.adapter;

import eterea.tenant.service.tenant.hexagonal.empresa.domain.model.Empresa;
import eterea.tenant.service.tenant.hexagonal.empresa.domain.ports.out.EmpresaRepository;
import eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.persistence.mapper.EmpresaMapper;
import eterea.tenant.service.tenant.hexagonal.empresa.infrastructure.persistence.repository.JpaEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaEmpresaRepositoryAdapter implements EmpresaRepository {

    private final JpaEmpresaRepository jpaEmpresaRepository;
    private final EmpresaMapper empresaMapper;

    @Override
    public Optional<Empresa> findLast() {
        return jpaEmpresaRepository.findTopByOrderByEmpresaIdDesc()
                .map(empresaMapper::toDomainModel);
    }

    @Override
    public void save(Empresa empresa) {
        jpaEmpresaRepository.save(empresaMapper.toEntity(empresa));
    }

}
