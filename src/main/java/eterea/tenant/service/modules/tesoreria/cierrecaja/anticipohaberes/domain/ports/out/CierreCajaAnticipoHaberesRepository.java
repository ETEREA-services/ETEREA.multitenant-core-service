package eterea.tenant.service.modules.tesoreria.cierrecaja.anticipohaberes.domain.ports.out;

import eterea.tenant.service.modules.tesoreria.cierrecaja.anticipohaberes.domain.model.CierreCajaAnticipoHaberes;

import java.util.List;

public interface CierreCajaAnticipoHaberesRepository {

    List<CierreCajaAnticipoHaberes> saveAllAnticipoHaberesByCierreCaja(List<CierreCajaAnticipoHaberes> anticipos);

}
