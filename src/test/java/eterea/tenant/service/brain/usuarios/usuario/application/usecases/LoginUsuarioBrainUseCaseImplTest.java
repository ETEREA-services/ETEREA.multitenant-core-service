package eterea.tenant.service.brain.usuarios.usuario.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.usuarios.usuario.application.exception.TemporaryPasswordExpiredException;
import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.configuration.SecurityProperties;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginUsuarioBrainUseCaseImplTest {

    @Mock
    private UsuarioBrainRepository usuarioBrainRepository;

    @Mock
    private PasswordHashPort passwordHashPort;

    private LoginUsuarioBrainUseCaseImpl useCase;
    private UsuarioBrain usuario;

    @BeforeEach
    void setUp() {
        SecurityProperties properties = new SecurityProperties();
        properties.setMaxFailedAttempts(3);
        properties.setLockDuration(Duration.ofMinutes(15));
        useCase = new LoginUsuarioBrainUseCaseImpl(
                usuarioBrainRepository, passwordHashPort, properties);

        usuario = UsuarioBrain.builder()
                .usuarioId(UUID.randomUUID())
                .login("daniel")
                .nombre("Daniel")
                .passwordHash("hash")
                .activo(true)
                .administrador(false)
                .failedLoginAttempts((short) 0)
                .build();
    }

    @Test
    void loginValidoReseteaIntentosYRegistraIngreso() {
        when(usuarioBrainRepository.findByLogin("daniel")).thenReturn(Optional.of(usuario));
        when(passwordHashPort.matches("secreto", "hash")).thenReturn(true);
        when(usuarioBrainRepository.save(any(UsuarioBrain.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<UsuarioBrain> resultado = useCase.login("daniel", "secreto", "127.0.0.1");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getFailedLoginAttempts()).isZero();
        assertThat(resultado.get().getLastLoginIp()).isEqualTo("127.0.0.1");
        assertThat(resultado.get().getLastLoginAt()).isNotNull();
        verify(usuarioBrainRepository).save(usuario);
    }

    @Test
    void passwordIncorrectaIncrementaIntentos() {
        when(usuarioBrainRepository.findByLogin("daniel")).thenReturn(Optional.of(usuario));
        when(passwordHashPort.matches("mal", "hash")).thenReturn(false);

        Optional<UsuarioBrain> resultado = useCase.login("daniel", "mal", "127.0.0.1");

        assertThat(resultado).isEmpty();
        assertThat(usuario.getFailedLoginAttempts()).isEqualTo((short) 1);
        verify(usuarioBrainRepository).save(usuario);
    }

    @Test
    void tercerFalloBloqueaLaCuenta() {
        usuario.setFailedLoginAttempts((short) 2);
        when(usuarioBrainRepository.findByLogin("daniel")).thenReturn(Optional.of(usuario));
        when(passwordHashPort.matches(eq("mal"), eq("hash"))).thenReturn(false);

        useCase.login("daniel", "mal", "127.0.0.1");

        assertThat(usuario.getFailedLoginAttempts()).isEqualTo((short) 3);
        assertThat(usuario.getLockedUntil()).isNotNull();
        assertThat(usuario.getLockedUntil()).isAfter(java.time.OffsetDateTime.now());
    }

    @Test
    void usuarioBloqueadoNoValidaPassword() {
        usuario.setLockedUntil(java.time.OffsetDateTime.now().plusMinutes(5));
        when(usuarioBrainRepository.findByLogin("daniel")).thenReturn(Optional.of(usuario));

        Optional<UsuarioBrain> resultado = useCase.login("daniel", "secreto", "127.0.0.1");

        assertThat(resultado).isEmpty();
        verify(usuarioBrainRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    void usuarioInactivoNoIngresa() {
        usuario.setActivo(false);
        when(usuarioBrainRepository.findByLogin("daniel")).thenReturn(Optional.of(usuario));

        Optional<UsuarioBrain> resultado = useCase.login("daniel", "secreto", "127.0.0.1");

        assertThat(resultado).isEmpty();
        verify(usuarioBrainRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    void passwordTemporalVencidaNoIngresa() {
        usuario.setMustChangePassword(true);
        usuario.setTemporaryPasswordExpiresAt(java.time.OffsetDateTime.now().minusMinutes(1));
        when(usuarioBrainRepository.findByLogin("daniel")).thenReturn(Optional.of(usuario));
        when(passwordHashPort.matches("temporal", "hash")).thenReturn(true);

        assertThatThrownBy(() -> useCase.login("daniel", "temporal", "127.0.0.1"))
                .isInstanceOf(TemporaryPasswordExpiredException.class);
        verify(usuarioBrainRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    void passwordTemporalVigentePermiteIngresar() {
        usuario.setMustChangePassword(true);
        usuario.setTemporaryPasswordExpiresAt(java.time.OffsetDateTime.now().plusHours(1));
        when(usuarioBrainRepository.findByLogin("daniel")).thenReturn(Optional.of(usuario));
        when(passwordHashPort.matches("temporal", "hash")).thenReturn(true);
        when(usuarioBrainRepository.save(any(UsuarioBrain.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Optional<UsuarioBrain> resultado = useCase.login("daniel", "temporal", "127.0.0.1");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getMustChangePassword()).isTrue();
    }
}
