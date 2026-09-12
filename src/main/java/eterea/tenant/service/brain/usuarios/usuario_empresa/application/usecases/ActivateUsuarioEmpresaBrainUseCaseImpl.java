package eterea.tenant.service.brain.usuarios.usuario_empresa.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.in.ActivateUsuarioEmpresaBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivateUsuarioEmpresaBrainUseCaseImpl implements ActivateUsuarioEmpresaBrainUseCase {

    private final UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;

    @Override
    public Optional<UsuarioEmpresaBrain> activate(UUID usuarioEmpresaId) {
        return usuarioEmpresaBrainRepository.findById(usuarioEmpresaId)
                .map(existing -> {
                    existing.setActivo(Boolean.TRUE);
                    return usuarioEmpresaBrainRepository.save(existing);
                });
    }
}
