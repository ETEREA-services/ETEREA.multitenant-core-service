package eterea.tenant.service.brain.usuarios.usuario.application.usecases;

import eterea.tenant.service.brain.auth.application.service.RefreshTokenService;
import eterea.tenant.service.brain.usuarios.usuario.application.exception.UsuarioBrainException;
import eterea.tenant.service.brain.usuarios.usuario.application.service.TemporaryPasswordGenerator;
import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.in.ResetUsuarioBrainPasswordUseCase;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.configuration.SecurityProperties;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ResetUsuarioBrainPasswordUseCaseImpl implements ResetUsuarioBrainPasswordUseCase {

    private final UsuarioBrainRepository usuarioBrainRepository;
    private final PasswordHashPort passwordHashPort;
    private final TemporaryPasswordGenerator temporaryPasswordGenerator;
    private final RefreshTokenService refreshTokenService;
    private final SecurityProperties securityProperties;

    @Override
    @Transactional
    public TemporaryPassword reset(UUID usuarioId) {
        UsuarioBrain usuario = usuarioBrainRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioBrainException(usuarioId));

        String passwordTemporal = temporaryPasswordGenerator.generate();
        OffsetDateTime now = OffsetDateTime.now();

        usuario.setPasswordHash(passwordHashPort.hash(passwordTemporal));
        usuario.setPasswordChangedAt(now);
        usuario.setMustChangePassword(true);
        usuario.setTemporaryPasswordExpiresAt(now.plus(securityProperties.getTemporaryPasswordTtl()));
        usuario.setFailedLoginAttempts((short) 0);
        usuario.setLockedUntil(null);
        usuarioBrainRepository.save(usuario);

        refreshTokenService.revokeAllForUsuario(usuarioId.toString());

        return new TemporaryPassword(passwordTemporal, usuario.getTemporaryPasswordExpiresAt());
    }
}
