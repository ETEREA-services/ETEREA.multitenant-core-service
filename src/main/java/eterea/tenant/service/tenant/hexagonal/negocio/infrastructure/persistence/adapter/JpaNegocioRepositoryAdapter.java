package eterea.tenant.service.tenant.hexagonal.negocio.infrastructure.persistence.adapter;

import eterea.tenant.service.tenant.hexagonal.negocio.domain.model.Negocio;
import eterea.tenant.service.tenant.hexagonal.negocio.domain.ports.out.NegocioRepository;
import eterea.tenant.service.tenant.hexagonal.negocio.infrastructure.persistence.mapper.NegocioMapper;
import eterea.tenant.service.tenant.hexagonal.negocio.infrastructure.persistence.repository.JpaNegocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaNegocioRepositoryAdapter implements NegocioRepository {

    private final JpaNegocioRepository jpaNegocioRepository;
    private final NegocioMapper negocioMapper;

    @Override
    public List<Negocio> findAllByCopyArticulo(Byte copyArticulo) {
        return jpaNegocioRepository.findAllByCopyArticulo(copyArticulo)
                .stream()
                .map(negocioMapper::toDomainModel)
                .toList();
    }

    @Override
    public List<Negocio> findAllWithIpAddress(Integer negocioId) {
        if (negocioId > 0) {
            return jpaNegocioRepository.findAllByDatabaseIpVpnIsNotNullAndDatabaseIpVpnIsNotAndNegocioIdIsNot("", negocioId)
                    .stream()
                    .map(negocioMapper::toDomainModel)
                    .toList();
        }
        return  jpaNegocioRepository.findAllByDatabaseIpVpnIsNotNullAndDatabaseIpVpnIsNot("")
                .stream()
                .map(negocioMapper::toDomainModel)
                .toList();
    }

    @Override
    public Optional<Negocio> findByNegocioId(Integer negocioId) {
        return jpaNegocioRepository.findByNegocioId(negocioId)
                .map(negocioEntity -> {
                    Negocio negocio = negocioMapper.toDomainModel(negocioEntity);
                    negocio.setIpAddress(negocio.getDatabaseIpVpn());
                    negocio.setBackendServer(negocio.getBackendIpVpn());
                    return negocio;
                });
    }

}
