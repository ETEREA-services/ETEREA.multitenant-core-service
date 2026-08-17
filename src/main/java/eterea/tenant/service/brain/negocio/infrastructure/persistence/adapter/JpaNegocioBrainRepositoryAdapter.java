package eterea.tenant.service.brain.negocio.infrastructure.persistence.adapter;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import eterea.tenant.service.brain.negocio.infrastructure.persistence.mapper.NegocioBrainMapper;
import eterea.tenant.service.brain.negocio.infrastructure.persistence.repository.JpaNegocioBrainRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaNegocioBrainRepositoryAdapter implements NegocioBrainRepository {

    private final JpaNegocioBrainRepository jpaNegocioBrainRepository;
    private final NegocioBrainMapper negocioBrainMapper;

    @Override
    public List<NegocioBrain> findAll() {
        return jpaNegocioBrainRepository.findAll().stream()
                .map(negocioBrainMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<NegocioBrain> findById(Integer negocioId) {
        return jpaNegocioBrainRepository.findById(negocioId)
                .map(negocioBrainMapper::toDomain);
    }

    @Override
    public Optional<NegocioBrain> findByTenantId(String tenantId) {
        return jpaNegocioBrainRepository.findByTenantId(tenantId)
                .map(negocioBrainMapper::toDomain);
    }

    @Override
    public NegocioBrain save(NegocioBrain negocio) {
        return negocioBrainMapper.toDomain(
                jpaNegocioBrainRepository.save(negocioBrainMapper.toEntity(negocio)));
    }
}
