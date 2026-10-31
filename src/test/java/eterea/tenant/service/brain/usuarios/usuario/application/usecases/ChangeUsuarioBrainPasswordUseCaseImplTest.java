package eterea.tenant.service.brain.usuarios.usuario.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.PasswordHashPort;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChangeUsuarioBrainPasswordUseCaseImplTest {

    @Mock
    private UsuarioBrainRepository usuarioBrainRepository;

    @Mock
    private PasswordHashPort passwordHashPort;

    private ChangeUsuarioBrainPasswordUseCaseImpl useCase;
    private UsuarioBrain usuario;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        useCase = new ChangeUsuarioBrainPasswordUseCaseImpl(
                usuarioBrainRepository, passwordHashPort);

        usuarioId = UUID.randomUUID();
        usuario = UsuarioBrain.builder()
                .usuarioId(usuarioId)
                .login("daniel")
                .passwordHash("hash-temporal")
                .mustChangePassword(true)
                .temporaryPasswordExpiresAt(OffsetDateTime.now().plusHours(12))
                .build();
    }

    @Test
    void cambioExitosoLimpiaElFlagDeTemporal() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(passwordHashPort.matches("temporal", "hash-temporal")).thenReturn(true);
        when(passwordHashPort.hash("nueva-clave")).thenReturn("hash-nuevo");
        when(usuarioBrainRepository.save(any(UsuarioBrain.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        boolean resultado = useCase.change(usuarioId, "temporal", "nueva-clave");

        assertThat(resultado).isTrue();
        assertThat(usuario.getPasswordHash()).isEqualTo("hash-nuevo");
        assertThat(usuario.getMustChangePassword()).isFalse();
        assertThat(usuario.getTemporaryPasswordExpiresAt()).isNull();
        verify(usuarioBrainRepository).save(usuario);
    }

    @Test
    void passwordActualIncorrectaNoCambiaNada() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(passwordHashPort.matches(anyString(), anyString())).thenReturn(false);

        boolean resultado = useCase.change(usuarioId, "mal", "nueva-clave");

        assertThat(resultado).isFalse();
        assertThat(usuario.getMustChangePassword()).isTrue();
        verify(usuarioBrainRepository, never()).save(any());
    }
}
