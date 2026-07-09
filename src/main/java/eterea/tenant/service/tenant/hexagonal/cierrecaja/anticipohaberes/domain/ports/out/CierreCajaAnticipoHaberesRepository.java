package eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.ports.out;

import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.CierreCajaAnticipoHaberes;

import java.util.List;

public interface CierreCajaAnticipoHaberesRepository {

    List<CierreCajaAnticipoHaberes> saveAllAnticipoHaberesByCierreCaja(List<CierreCajaAnticipoHaberes> anticipos);

}
