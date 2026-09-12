package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.in.ActivateUsuarioNegocioBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivateUsuarioNegocioBrainUseCaseImpl implements ActivateUsuarioNegocioBrainUseCase {

    private final UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;

    @Override
    public Optional<UsuarioNegocioBrain> activate(UUID usuarioNegocioId) {
        return usuarioNegocioBrainRepository.findById(usuarioNegocioId)
                .map(existing -> {
                    existing.setActivo(Boolean.TRUE);
                    return usuarioNegocioBrainRepository.save(existing);
                });
    }
}
