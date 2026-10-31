package eterea.tenant.service.brain.usuarios.usuario.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.auth.application.service.RefreshTokenService;
import eterea.tenant.service.brain.usuarios.usuario.application.exception.UsuarioBrainException;
import eterea.tenant.service.brain.usuarios.usuario.application.service.TemporaryPasswordGenerator;
import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.in.ResetUsuarioBrainPasswordUseCase;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.configuration.SecurityProperties;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResetUsuarioBrainPasswordUseCaseImplTest {

    @Mock
    private UsuarioBrainRepository usuarioBrainRepository;

    @Mock
    private PasswordHashPort passwordHashPort;

    @Mock
    private RefreshTokenService refreshTokenService;

    private ResetUsuarioBrainPasswordUseCaseImpl useCase;
    private UsuarioBrain usuario;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        SecurityProperties properties = new SecurityProperties();
        properties.setTemporaryPasswordTtl(Duration.ofHours(24));
        useCase = new ResetUsuarioBrainPasswordUseCaseImpl(
                usuarioBrainRepository,
                passwordHashPort,
                new TemporaryPasswordGenerator(),
                refreshTokenService,
                properties);

        usuarioId = UUID.randomUUID();
        usuario = UsuarioBrain.builder()
                .usuarioId(usuarioId)
                .login("daniel")
                .nombre("Daniel")
                .passwordHash("hash-viejo")
                .activo(true)
                .administrador(false)
                .failedLoginAttempts((short) 5)
                .lockedUntil(OffsetDateTime.now().plusMinutes(10))
                .mustChangePassword(false)
                .build();
    }

    @Test
    void blanqueoGeneraTemporalYExigeCambio() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(passwordHashPort.hash(any(String.class))).thenReturn("hash-temporal");
        when(usuarioBrainRepository.save(any(UsuarioBrain.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResetUsuarioBrainPasswordUseCase.TemporaryPassword resultado = useCase.reset(usuarioId);

        assertThat(resultado.temporaryPassword()).hasSize(10);
        assertThat(resultado.expiresAt()).isAfter(OffsetDateTime.now().plusHours(23));
        assertThat(usuario.getPasswordHash()).isEqualTo("hash-temporal");
        assertThat(usuario.getMustChangePassword()).isTrue();
        assertThat(usuario.getTemporaryPasswordExpiresAt()).isEqualTo(resultado.expiresAt());
        assertThat(usuario.getFailedLoginAttempts()).isZero();
        assertThat(usuario.getLockedUntil()).isNull();
        verify(refreshTokenService).revokeAllForUsuario(usuarioId.toString());
    }

    @Test
    void usuarioInexistenteFalla() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.reset(usuarioId))
                .isInstanceOf(UsuarioBrainException.class);
    }
}
