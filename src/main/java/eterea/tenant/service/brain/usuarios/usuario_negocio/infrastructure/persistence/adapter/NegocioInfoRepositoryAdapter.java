package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.adapter;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.NegocioInfoRepository;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NegocioInfoRepositoryAdapter implements NegocioInfoRepository {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    public List<NegocioInfo> findAllByEmpresaId(UUID empresaId) {
        return negocioBrainRepository.findAllByEmpresaId(empresaId).stream()
                .map(this::toInfo)
                .toList();
    }

    @Override
    public List<NegocioInfo> findAllByIds(Collection<Integer> negocioIds) {
        if (negocioIds.isEmpty()) {
            return List.of();
        }
        return negocioBrainRepository.findAllById(negocioIds).stream()
                .map(this::toInfo)
                .toList();
    }

    private NegocioInfo toInfo(NegocioBrain negocio) {
        return NegocioInfo.builder()
                .negocioId(negocio.getNegocioId())
                .empresaId(negocio.getEmpresaId())
                .nombre(negocio.getNombre())
                .tenantId(negocio.getTenantId())
                .build();
    }
}
