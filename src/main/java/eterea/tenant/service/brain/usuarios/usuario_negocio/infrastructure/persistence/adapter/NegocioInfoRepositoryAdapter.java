package eterea.tenant.service.brain.usuarios.usuario_negocio.infrastructure.persistence.adapter;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.NegocioInfoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NegocioInfoRepositoryAdapter implements NegocioInfoRepository {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    public Optional<NegocioInfo> findById(Integer negocioId) {
        return negocioBrainRepository.findById(negocioId)
                .map(this::toInfo);
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
