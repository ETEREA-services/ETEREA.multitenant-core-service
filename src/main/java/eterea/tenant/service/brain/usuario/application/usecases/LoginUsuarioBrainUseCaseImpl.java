package eterea.tenant.service.brain.usuario.application.usecases;

import eterea.tenant.service.brain.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuario.domain.ports.in.LoginUsuarioBrainUseCase;
import eterea.tenant.service.brain.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuario.domain.ports.out.UsuarioBrainRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUsuarioBrainUseCaseImpl implements LoginUsuarioBrainUseCase {

    private final UsuarioBrainRepository usuarioBrainRepository;
    private final PasswordHashPort passwordHashPort;

    @Override
    public Optional<UsuarioBrain> login(String login, String password, String ip) {
        return usuarioBrainRepository.findByLogin(login)
                .filter(usuario -> Boolean.TRUE.equals(usuario.getActivo()))
                .filter(usuario -> usuario.getLockedUntil() == null
                        || usuario.getLockedUntil().isBefore(OffsetDateTime.now()))
                .filter(usuario -> passwordHashPort.matches(password, usuario.getPasswordHash()))
                .map(usuario -> {
                    usuario.setLastLoginAt(OffsetDateTime.now());
                    usuario.setLastLoginIp(ip);
                    usuario.setFailedLoginAttempts((short) 0);
                    return usuarioBrainRepository.save(usuario);
                });
    }
}
