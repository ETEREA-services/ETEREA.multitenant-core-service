package eterea.tenant.service.brain.usuario.application.usecases;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuario.domain.ports.in.CreateUsuarioBrainUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuario.domain.ports.out.UsuarioBrainRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUsuarioBrainUseCaseImpl implements CreateUsuarioBrainUseCase {

    private final UsuarioBrainRepository usuarioBrainRepository;
    private final PasswordHashPort passwordHashPort;

    @Override
    public UsuarioBrain create(UsuarioBrain usuario, String password) {
        usuario.setUsuarioId(usuario.getUsuarioId() == null ? UUID.randomUUID() : usuario.getUsuarioId());
        usuario.setPasswordHash(passwordHashPort.hash(password));
        usuario.setPasswordChangedAt(OffsetDateTime.now());
        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }
        if (usuario.getFailedLoginAttempts() == null) {
            usuario.setFailedLoginAttempts((short) 0);
        }
        return usuarioBrainRepository.save(usuario);
    }
}
