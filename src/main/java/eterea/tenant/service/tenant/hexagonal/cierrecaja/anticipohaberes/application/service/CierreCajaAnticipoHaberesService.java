package eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.application.service;

import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.AnticipoHaberes;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.CierreCajaAnticipoHaberes;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.ports.in.SaveAllAnticipoHaberesUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CierreCajaAnticipoHaberesService {

    private final SaveAllAnticipoHaberesUseCase saveAllAnticipoHaberesUseCase;

    public CierreCajaAnticipoHaberesService(SaveAllAnticipoHaberesUseCase saveAllAnticipoHaberesUseCase) {
        this.saveAllAnticipoHaberesUseCase = saveAllAnticipoHaberesUseCase;
    }

    public List<CierreCajaAnticipoHaberes> saveAllAnticipoHaberes(Long cierreCajaId, Long userId, List<AnticipoHaberes> anticipos) {
        return saveAllAnticipoHaberesUseCase.saveAllAnticipoHaberes(cierreCajaId, userId, anticipos);
    }

}
