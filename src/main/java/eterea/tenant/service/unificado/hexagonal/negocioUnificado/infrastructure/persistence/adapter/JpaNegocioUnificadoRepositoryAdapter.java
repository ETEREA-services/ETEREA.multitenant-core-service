package eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.adapter;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.ports.out.NegocioUnificadoRepository;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.mapper.NegocioUnificadoMapper;
import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.repository.JpaNegocioUnificadoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaNegocioUnificadoRepositoryAdapter implements NegocioUnificadoRepository {

    private final JpaNegocioUnificadoRepository jpaRepository;
    private final NegocioUnificadoMapper mapper;

    public JpaNegocioUnificadoRepositoryAdapter(JpaNegocioUnificadoRepository jpaRepository,
                                                NegocioUnificadoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<NegocioUnificado> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NegocioUnificado> findByNegocioId(Integer negocioId) {
        return jpaRepository.findByNegocioId(negocioId)
                .map(mapper::toDomain);
    }

}
