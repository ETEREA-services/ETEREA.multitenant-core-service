package eterea.tenant.service.brain.usuario.application.usecases;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuario.domain.ports.in.GetUsuarioBrainByIdUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.out.UsuarioBrainRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUsuarioBrainByIdUseCaseImpl implements GetUsuarioBrainByIdUseCase {

    private final UsuarioBrainRepository usuarioBrainRepository;

    @Override
    public Optional<UsuarioBrain> getById(UUID usuarioId) {
        return usuarioBrainRepository.findById(usuarioId);
    }
}
