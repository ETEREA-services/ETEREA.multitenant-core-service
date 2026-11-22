package eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NegocioInfoRepository {

    /**
     * Negocios de una empresa en una sola consulta, para evitar el N+1 al
     * reconstruir el contexto del usuario.
     */
    List<NegocioInfo> findAllByEmpresaId(UUID empresaId);

    /**
     * Negocios por id en una sola consulta, para resolver las asignaciones de
     * un usuario sin un viaje a la base por cada una.
     */
    List<NegocioInfo> findAllByIds(Collection<Integer> negocioIds);
}
