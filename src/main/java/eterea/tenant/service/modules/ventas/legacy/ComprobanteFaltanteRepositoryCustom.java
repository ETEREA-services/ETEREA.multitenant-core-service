package eterea.tenant.service.modules.ventas.legacy;

import java.time.OffsetDateTime;

public interface ComprobanteFaltanteRepositoryCustom {

    void deleteAllByFecha(OffsetDateTime fecha);

}
