package eterea.tenant.service.brain.empresa.infrastructure.persistence.adapter;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.domain.ports.out.EmpresaBrainRepository;
import eterea.tenant.service.brain.empresa.infrastructure.persistence.mapper.EmpresaBrainMapper;
import eterea.tenant.service.brain.empresa.infrastructure.persistence.repository.JpaEmpresaBrainRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaEmpresaBrainRepositoryAdapter implements EmpresaBrainRepository {

    private final JpaEmpresaBrainRepository jpaEmpresaBrainRepository;
    private final EmpresaBrainMapper empresaBrainMapper;

    @Override
    public List<EmpresaBrain> findAll() {
        return jpaEmpresaBrainRepository.findAll().stream()
                .map(empresaBrainMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<EmpresaBrain> findById(UUID empresaId) {
        return jpaEmpresaBrainRepository.findById(empresaId)
                .map(empresaBrainMapper::toDomain);
    }

    @Override
    public EmpresaBrain save(EmpresaBrain empresa) {
        return empresaBrainMapper.toDomain(
                jpaEmpresaBrainRepository.save(empresaBrainMapper.toEntity(empresa)));
    }
}
