package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
import eterea.tenant.service.modules.platform.legacy.Usuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario.domain.model.UsuarioBrain;
import eterea.tenant.service.brain.usuarios.usuario.domain.ports.out.UsuarioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.application.exception.UsuarioNegocioBrainConflictException;
import eterea.tenant.service.brain.usuarios.usuario_negocio.application.exception.UsuarioNegocioBrainNotFoundException;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateUsuarioNegocioBrainUseCaseImplTest {

    @Mock
    private UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;

    @Mock
    private UsuarioBrainRepository usuarioBrainRepository;

    @Mock
    private NegocioBrainRepository negocioBrainRepository;

    @Mock
    private UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;

    private CreateUsuarioNegocioBrainUseCaseImpl useCase;
    private UUID usuarioId;
    private UUID empresaId;
    private Integer negocioId;

    @BeforeEach
    void setUp() {
        useCase = new CreateUsuarioNegocioBrainUseCaseImpl(
                usuarioNegocioBrainRepository,
                usuarioBrainRepository,
                negocioBrainRepository,
                usuarioEmpresaBrainRepository);
        usuarioId = UUID.randomUUID();
        empresaId = UUID.randomUUID();
        negocioId = 10;
    }

    @Test
    void creaAsignacionNuevaConEmpresaActiva() {
        prepararUsuarioYNegocio();
        when(usuarioEmpresaBrainRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId))
                .thenReturn(Optional.of(asignacionEmpresa(true)));
        when(usuarioNegocioBrainRepository.findByUsuarioIdAndNegocioId(usuarioId, negocioId))
                .thenReturn(Optional.empty());
        when(usuarioNegocioBrainRepository.save(any(UsuarioNegocioBrain.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioNegocioBrain resultado = useCase.create(UsuarioNegocioBrain.builder()
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .build());

        assertThat(resultado.getUsuarioNegocioId()).isNotNull();
        assertThat(resultado.getActivo()).isTrue();
    }

    @Test
    void reactivaAsignacionInactivaExistente() {
        UUID asignacionId = UUID.randomUUID();
        UsuarioNegocioBrain existente = UsuarioNegocioBrain.builder()
                .usuarioNegocioId(asignacionId)
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .activo(false)
                .build();
        prepararUsuarioYNegocio();
        when(usuarioEmpresaBrainRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId))
                .thenReturn(Optional.of(asignacionEmpresa(true)));
        when(usuarioNegocioBrainRepository.findByUsuarioIdAndNegocioId(usuarioId, negocioId))
                .thenReturn(Optional.of(existente));
        when(usuarioNegocioBrainRepository.save(existente)).thenReturn(existente);

        UsuarioNegocioBrain resultado = useCase.create(UsuarioNegocioBrain.builder()
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .build());

        assertThat(resultado.getUsuarioNegocioId()).isEqualTo(asignacionId);
        assertThat(resultado.getActivo()).isTrue();
    }

    @Test
    void asignacionActivaDuplicadaFalla() {
        prepararUsuarioYNegocio();
        when(usuarioEmpresaBrainRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId))
                .thenReturn(Optional.of(asignacionEmpresa(true)));
        when(usuarioNegocioBrainRepository.findByUsuarioIdAndNegocioId(usuarioId, negocioId))
                .thenReturn(Optional.of(UsuarioNegocioBrain.builder()
                        .usuarioId(usuarioId)
                        .negocioId(negocioId)
                        .activo(true)
                        .build()));

        assertThatThrownBy(() -> useCase.create(UsuarioNegocioBrain.builder()
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .build()))
                .isInstanceOf(UsuarioNegocioBrainConflictException.class);
    }

    @Test
    void empresaNoAsignadaFalla() {
        prepararUsuarioYNegocio();
        when(usuarioEmpresaBrainRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(UsuarioNegocioBrain.builder()
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .build()))
                .isInstanceOf(UsuarioNegocioBrainConflictException.class);
    }

    @Test
    void empresaAsignadaInactivaFalla() {
        prepararUsuarioYNegocio();
        when(usuarioEmpresaBrainRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId))
                .thenReturn(Optional.of(asignacionEmpresa(false)));

        assertThatThrownBy(() -> useCase.create(UsuarioNegocioBrain.builder()
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .build()))
                .isInstanceOf(UsuarioNegocioBrainConflictException.class);
    }

    @Test
    void usuarioInexistenteFalla() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(UsuarioNegocioBrain.builder()
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .build()))
                .isInstanceOf(UsuarioNegocioBrainNotFoundException.class);
    }

    @Test
    void negocioInexistenteFalla() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario()));
        when(negocioBrainRepository.findById(negocioId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(UsuarioNegocioBrain.builder()
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .build()))
                .isInstanceOf(UsuarioNegocioBrainNotFoundException.class);
    }

    private void prepararUsuarioYNegocio() {
        when(usuarioBrainRepository.findById(usuarioId)).thenReturn(Optional.of(usuario()));
        when(negocioBrainRepository.findById(negocioId)).thenReturn(Optional.of(negocio()));
    }

    private UsuarioBrain usuario() {
        return UsuarioBrain.builder()
                .usuarioId(usuarioId)
                .login("usuario")
                .nombre("Usuario")
                .activo(true)
                .build();
    }

    private NegocioBrain negocio() {
        return NegocioBrain.builder()
                .negocioId(negocioId)
                .empresaId(empresaId)
                .nombre("Negocio")
                .tenantId("tenant-1")
                .build();
    }

    private UsuarioEmpresaBrain asignacionEmpresa(boolean activo) {
        return UsuarioEmpresaBrain.builder()
                .usuarioEmpresaId(UUID.randomUUID())
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .activo(activo)
                .build();
    }
}
