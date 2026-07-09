package eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.application.usecases;

import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.AnticipoHaberes;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.CierreCajaAnticipoHaberes;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.ports.in.SaveAllAnticipoHaberesUseCase;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.ports.out.CierreCajaAnticipoHaberesRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaveAllAnticipoHaberesUseCaseImpl implements SaveAllAnticipoHaberesUseCase {

    private final CierreCajaAnticipoHaberesRepository repository;

    public SaveAllAnticipoHaberesUseCaseImpl(CierreCajaAnticipoHaberesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CierreCajaAnticipoHaberes> saveAllAnticipoHaberes(Long cierreCajaId, Long userId, List<AnticipoHaberes> anticipos) {
        List<CierreCajaAnticipoHaberes> anticiposComplete = anticipos.stream()
                .map(anticipo -> CierreCajaAnticipoHaberes.builder()
                        .cierreCajaId(cierreCajaId)
                        .legajoId(anticipo.getLegajoId())
                        .fecha(anticipo.getFecha())
                        .importe(anticipo.getImporte())
                        .userId(userId)
                        .build())
                .toList();
        return repository.saveAllAnticipoHaberesByCierreCaja(anticiposComplete);
    }

}
