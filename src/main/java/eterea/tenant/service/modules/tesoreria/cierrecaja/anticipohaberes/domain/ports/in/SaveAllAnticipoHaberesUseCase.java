package eterea.tenant.service.modules.tesoreria.cierrecaja.anticipohaberes.domain.ports.in;

import eterea.tenant.service.modules.tesoreria.cierrecaja.anticipohaberes.domain.model.AnticipoHaberes;
import eterea.tenant.service.modules.tesoreria.cierrecaja.anticipohaberes.domain.model.CierreCajaAnticipoHaberes;

import java.util.List;

public interface SaveAllAnticipoHaberesUseCase {

    List<CierreCajaAnticipoHaberes> saveAllAnticipoHaberes(Long cierreCajaId, Long userId, List<AnticipoHaberes> anticipos);

}
