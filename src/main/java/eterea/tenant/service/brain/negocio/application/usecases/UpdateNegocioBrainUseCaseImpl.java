package eterea.tenant.service.brain.negocio.application.usecases;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.in.UpdateNegocioBrainUseCase;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateNegocioBrainUseCaseImpl implements UpdateNegocioBrainUseCase {

    private final NegocioBrainRepository negocioBrainRepository;

    @Override
    public Optional<NegocioBrain> update(Integer negocioId, NegocioBrain negocio) {
        return negocioBrainRepository.findById(negocioId)
                .map(existing -> {
                    existing.setEmpresaId(negocio.getEmpresaId());
                    existing.setNombre(negocio.getNombre());
                    existing.setDatabaseName(negocio.getDatabaseName());
                    existing.setDatabaseIp(negocio.getDatabaseIp());
                    existing.setDatabasePort(negocio.getDatabasePort());
                    existing.setDatabaseUsername(negocio.getDatabaseUsername());
                    existing.setDatabasePassword(negocio.getDatabasePassword());
                    existing.setBackendIp(negocio.getBackendIp());
                    existing.setBackendPort(negocio.getBackendPort());
                    existing.setTenantId(negocio.getTenantId());
                    return negocioBrainRepository.save(existing);
                });
    }
}
