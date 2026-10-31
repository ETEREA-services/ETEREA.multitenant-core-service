package eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.domain.model.Transferencia;
import eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.domain.ports.out.TransferenciaRepository;
import eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.infrastructure.persistence.mapper.TransferenciaMapper;
import eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.infrastructure.persistence.repository.JpaTransferenciaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaTransferenciaRepositoryAdapter implements TransferenciaRepository {

    private final JpaTransferenciaRepository jpaRepository;
    private final TransferenciaMapper mapper;

    public JpaTransferenciaRepositoryAdapter(JpaTransferenciaRepository jpaRepository, TransferenciaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Transferencia> findByNegocioIdDesdeAndNegocioIdHastaAndNumeroTransferencia(
            Integer negocioIdDesde, Integer negocioIdHasta, Long numeroTransferencia) {
        return jpaRepository.findByNegocioIdDesdeAndNegocioIdHastaAndNumeroTransferencia(
                negocioIdDesde, negocioIdHasta, numeroTransferencia
        ).map(mapper::toDomain);
    }
}
