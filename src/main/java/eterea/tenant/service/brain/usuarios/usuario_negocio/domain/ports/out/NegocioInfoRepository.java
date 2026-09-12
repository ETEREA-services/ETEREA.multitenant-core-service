package eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import java.util.Optional;

public interface NegocioInfoRepository {

    Optional<NegocioInfo> findById(Integer negocioId);
}
