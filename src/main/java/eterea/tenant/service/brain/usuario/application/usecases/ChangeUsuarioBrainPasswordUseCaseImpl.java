package eterea.tenant.service.brain.usuario.application.usecases;

import eterea.tenant.service.brain.usuario.domain.ports.in.ChangeUsuarioBrainPasswordUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuario.domain.ports.out.UsuarioBrainRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeUsuarioBrainPasswordUseCaseImpl implements ChangeUsuarioBrainPasswordUseCase {

    private final UsuarioBrainRepository usuarioBrainRepository;
    private final PasswordHashPort passwordHashPort;

    @Override
    public boolean change(UUID usuarioId, String currentPassword, String newPassword) {
        return usuarioBrainRepository.findById(usuarioId)
                .filter(usuario -> passwordHashPort.matches(currentPassword, usuario.getPasswordHash()))
                .map(usuario -> {
                    usuario.setPasswordHash(passwordHashPort.hash(newPassword));
                    usuario.setPasswordChangedAt(OffsetDateTime.now());
                    usuarioBrainRepository.save(usuario);
                    return true;
                })
                .orElse(false);
    }
}
