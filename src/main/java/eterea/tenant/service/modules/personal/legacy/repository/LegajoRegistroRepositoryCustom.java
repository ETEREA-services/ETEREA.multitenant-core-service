package eterea.tenant.service.modules.personal.legacy.repository;

import eterea.tenant.service.modules.personal.legacy.LegajoRegistro;
import java.sql.Time;
import java.util.Date;
import java.util.Optional;

public interface LegajoRegistroRepositoryCustom {

    Optional<LegajoRegistro> findLastByLegajoId(Integer legajoId);

    Optional<LegajoRegistro> findLastByLegajoIdAndFecha(Integer legajoId, Date fecha, Time hora);

}
