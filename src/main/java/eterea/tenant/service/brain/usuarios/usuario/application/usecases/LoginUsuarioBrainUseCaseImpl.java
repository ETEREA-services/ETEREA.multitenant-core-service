package eterea.tenant.service.brain.usuarios.usuario.application.usecases;

import eterea.tenant.service.brain.usuarios.usuario.application.exception.TemporaryPasswordExpiredException;
import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.in.LoginUsuarioBrainUseCase;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.configuration.SecurityProperties;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LoginUsuarioBrainUseCaseImpl implements LoginUsuarioBrainUseCase {

    private final UsuarioBrainRepository usuarioBrainRepository;
    private final PasswordHashPort passwordHashPort;
    private final SecurityProperties securityProperties;

    @Override
    @Transactional
    public Optional<UsuarioBrain> login(String login, String password, String ip) {
        Optional<UsuarioBrain> usuarioOpt = usuarioBrainRepository.findByLogin(login);
        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        UsuarioBrain usuario = usuarioOpt.get();
        OffsetDateTime now = OffsetDateTime.now();

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            return Optional.empty();
        }

        if (usuario.getLockedUntil() != null && usuario.getLockedUntil().isBefore(now)) {
            usuario.setLockedUntil(null);
            usuario.setFailedLoginAttempts((short) 0);
        }

        if (usuario.getLockedUntil() != null && usuario.getLockedUntil().isAfter(now)) {
            return Optional.empty();
        }

        if (!passwordHashPort.matches(password, usuario.getPasswordHash())) {
            short intentos = (short) (
                    (usuario.getFailedLoginAttempts() == null ? 0 : usuario.getFailedLoginAttempts()) + 1);
            usuario.setFailedLoginAttempts(intentos);
            if (intentos >= securityProperties.getMaxFailedAttempts()) {
                usuario.setLockedUntil(now.plus(securityProperties.getLockDuration()));
            }
            usuarioBrainRepository.save(usuario);
            return Optional.empty();
        }

        if (Boolean.TRUE.equals(usuario.getMustChangePassword())
                && usuario.getTemporaryPasswordExpiresAt() != null
                && !usuario.getTemporaryPasswordExpiresAt().isAfter(now)) {
            throw new TemporaryPasswordExpiredException();
        }

        usuario.setLastLoginAt(now);
        usuario.setLastLoginIp(ip);
        usuario.setFailedLoginAttempts((short) 0);
        usuario.setLockedUntil(null);
        return Optional.of(usuarioBrainRepository.save(usuario));
    }

}
