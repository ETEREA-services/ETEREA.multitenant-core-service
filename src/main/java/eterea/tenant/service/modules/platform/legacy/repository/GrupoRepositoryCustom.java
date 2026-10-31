package eterea.tenant.service.modules.platform.legacy.repository;

import java.time.OffsetDateTime;
import java.util.List;

public interface GrupoRepositoryCustom {

    List<Integer> findAllByFecha(OffsetDateTime fecha);

}
