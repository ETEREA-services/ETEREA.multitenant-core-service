package eterea.tenant.service.brain.usuarios.usuario_empresa.application.usecases;

import eterea.tenant.service.modules.platform.legacy.hexagonal.empresa.domain.model.Empresa;
import eterea.tenant.service.modules.platform.legacy.Usuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.empresa.domain.model.EmpresaBrain;
import eterea.tenant.service.brain.empresa.domain.ports.out.EmpresaBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_empresa.application.exception.UsuarioEmpresaBrainConflictException;
import eterea.tenant.service.brain.usuarios.usuario_empresa.application.exception.UsuarioEmpresaBrainNotFoundException;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateUsuarioEmpresaBrainUseCaseImplTest {

    @Mock
    private UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;

    @Mock
    private UsuarioBrainRepository usuarioBrainRepository;

    @Mock
    private EmpresaBrainRepository empresaBrainRepository;

    private CreateUsuarioEmpresaBrainUseCaseImpl useCase;
    private UUID usuarioId;
    private UUID empresaId;

    @BeforeEach
    void setUp() {
        useCase = new CreateUsuarioEmpresaBrainUseCaseImpl(
                usuarioEmpresaBrainRepository, usuarioBrainRepository, empresaBrainRepository);
        usuarioId = UUID.randomUUID();
        empresaId = UUID.randomUUID();
    }

    @Test
    void creaAsignacionNuevaYActiva() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario()));
        when(empresaBrainRepository.findById(empresaId)).thenReturn(Optional.of(empresa()));
        when(usuarioEmpresaBrainRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId))
                .thenReturn(Optional.empty());
        when(usuarioEmpresaBrainRepository.save(any(UsuarioEmpresaBrain.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioEmpresaBrain resultado = useCase.create(UsuarioEmpresaBrain.builder()
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .build());

        assertThat(resultado.getUsuarioEmpresaId()).isNotNull();
        assertThat(resultado.getActivo()).isTrue();
        assertThat(resultado.getUsuarioId()).isEqualTo(usuarioId);
        assertThat(resultado.getEmpresaId()).isEqualTo(empresaId);
    }

    @Test
    void reactivaAsignacionInactivaExistente() {
        UUID asignacionId = UUID.randomUUID();
        UsuarioEmpresaBrain existente = UsuarioEmpresaBrain.builder()
                .usuarioEmpresaId(asignacionId)
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .activo(false)
                .build();
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario()));
        when(empresaBrainRepository.findById(empresaId)).thenReturn(Optional.of(empresa()));
        when(usuarioEmpresaBrainRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId))
                .thenReturn(Optional.of(existente));
        when(usuarioEmpresaBrainRepository.save(existente)).thenReturn(existente);

        UsuarioEmpresaBrain resultado = useCase.create(UsuarioEmpresaBrain.builder()
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .build());

        assertThat(resultado.getUsuarioEmpresaId()).isEqualTo(asignacionId);
        assertThat(resultado.getActivo()).isTrue();
    }

    @Test
    void asignacionActivaDuplicadaFalla() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario()));
        when(empresaBrainRepository.findById(empresaId)).thenReturn(Optional.of(empresa()));
        when(usuarioEmpresaBrainRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId))
                .thenReturn(Optional.of(UsuarioEmpresaBrain.builder()
                        .usuarioId(usuarioId)
                        .empresaId(empresaId)
                        .activo(true)
                        .build()));

        assertThatThrownBy(() -> useCase.create(UsuarioEmpresaBrain.builder()
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .build()))
                .isInstanceOf(UsuarioEmpresaBrainConflictException.class);
    }

    @Test
    void usuarioInexistenteFalla() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(UsuarioEmpresaBrain.builder()
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .build()))
                .isInstanceOf(UsuarioEmpresaBrainNotFoundException.class);
    }

    @Test
    void empresaInexistenteFalla() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario()));
        when(empresaBrainRepository.findById(empresaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(UsuarioEmpresaBrain.builder()
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .build()))
                .isInstanceOf(UsuarioEmpresaBrainNotFoundException.class);
    }

    private UsuarioBrain usuario() {
        return UsuarioBrain.builder()
                .usuarioId(usuarioId)
                .login("usuario")
                .nombre("Usuario")
                .activo(true)
                .build();
    }

    private EmpresaBrain empresa() {
        return EmpresaBrain.builder()
                .empresaId(empresaId)
                .nombre("Empresa")
                .build();
    }
}
