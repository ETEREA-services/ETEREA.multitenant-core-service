package eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.persistence.repository;

import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.model.CierreCajaAnticipoHaberes;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.domain.ports.out.CierreCajaAnticipoHaberesRepository;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.persistence.entity.CierreCajaAnticipoHaberesEntity;
import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.persistence.mapper.AnticipoHaberesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaCierreCajaAnticipoHaberesRepositoryAdapter implements CierreCajaAnticipoHaberesRepository {

    private final JpaCierreCajaAnticipoHaberesRepository jpaCierreCajaAnticipoHaberesRepository;
    private final AnticipoHaberesMapper anticipoHaberesMapper;

    @Override
    public List<CierreCajaAnticipoHaberes> saveAllAnticipoHaberesByCierreCaja(List<CierreCajaAnticipoHaberes> anticipos) {
        List<CierreCajaAnticipoHaberesEntity> anticiposSaved = jpaCierreCajaAnticipoHaberesRepository.saveAll(anticipos.stream()
                .map(anticipoHaberesMapper::toEntity)
                .toList());
        return anticiposSaved.stream()
                .map(anticipoHaberesMapper::toDomainModel)
                .toList();
    }

}
