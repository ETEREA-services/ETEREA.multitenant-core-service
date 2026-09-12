package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.DeactivateUsuarioNegocioBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeactivateUsuarioNegocioBrainUseCaseImpl implements DeactivateUsuarioNegocioBrainUseCase {

    private final UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;

    @Override
    public Optional<UsuarioNegocioBrain> deactivate(UUID usuarioNegocioId) {
        return usuarioNegocioBrainRepository.findById(usuarioNegocioId)
                .map(existing -> {
                    existing.setActivo(Boolean.FALSE);
                    return usuarioNegocioBrainRepository.save(existing);
                });
    }
}
