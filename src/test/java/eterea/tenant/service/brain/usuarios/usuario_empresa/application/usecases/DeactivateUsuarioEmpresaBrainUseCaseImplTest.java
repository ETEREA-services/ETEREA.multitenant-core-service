package eterea.tenant.service.brain.usuarios.usuario_empresa.application.usecases;

import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.model.UsuarioEmpresaBrain;
import eterea.tenant.service.brain.usuarios.usuario_empresa.domain.ports.out.UsuarioEmpresaBrainRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeactivateUsuarioEmpresaBrainUseCaseImplTest {

    @Mock
    private UsuarioEmpresaBrainRepository usuarioEmpresaBrainRepository;

    @Mock
    private UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;

    @Mock
    private NegocioBrainRepository negocioBrainRepository;

    private DeactivateUsuarioEmpresaBrainUseCaseImpl useCase;
    private UUID usuarioId;
    private UUID empresaId;
    private UUID usuarioEmpresaId;

    @BeforeEach
    void setUp() {
        useCase = new DeactivateUsuarioEmpresaBrainUseCaseImpl(
                usuarioEmpresaBrainRepository, usuarioNegocioBrainRepository, negocioBrainRepository);
        usuarioId = UUID.randomUUID();
        empresaId = UUID.randomUUID();
        usuarioEmpresaId = UUID.randomUUID();
    }

    @Test
    void desactivaEmpresaYNegociosActivosEnCascada() {
        UsuarioEmpresaBrain empresa = UsuarioEmpresaBrain.builder()
                .usuarioEmpresaId(usuarioEmpresaId)
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .activo(true)
                .build();
        UsuarioNegocioBrain negocioActivo = UsuarioNegocioBrain.builder()
                .usuarioNegocioId(UUID.randomUUID())
                .usuarioId(usuarioId)
                .negocioId(10)
                .activo(true)
                .build();
        UsuarioNegocioBrain negocioInactivo = UsuarioNegocioBrain.builder()
                .usuarioNegocioId(UUID.randomUUID())
                .usuarioId(usuarioId)
                .negocioId(11)
                .activo(false)
                .build();

        when(usuarioEmpresaBrainRepository.findById(usuarioEmpresaId))
                .thenReturn(Optional.of(empresa));
        when(usuarioEmpresaBrainRepository.save(empresa)).thenReturn(empresa);
        when(negocioBrainRepository.findAllByEmpresaId(empresaId)).thenReturn(List.of(
                negocio(10), negocio(11)));
        when(usuarioNegocioBrainRepository.findByUsuarioIdAndNegocioIdIn(eq(usuarioId), anyCollection()))
                .thenReturn(List.of(negocioActivo, negocioInactivo));
        when(usuarioNegocioBrainRepository.save(any(UsuarioNegocioBrain.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = useCase.deactivate(usuarioEmpresaId);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getActivo()).isFalse();
        assertThat(negocioActivo.getActivo()).isFalse();
        assertThat(negocioInactivo.getActivo()).isFalse();
        verify(usuarioNegocioBrainRepository).save(negocioActivo);
        verify(usuarioNegocioBrainRepository, never()).save(negocioInactivo);
    }

    @Test
    void empresaSinNegociosNoConsultaAsignaciones() {
        UsuarioEmpresaBrain empresa = UsuarioEmpresaBrain.builder()
                .usuarioEmpresaId(usuarioEmpresaId)
                .usuarioId(usuarioId)
                .empresaId(empresaId)
                .activo(true)
                .build();

        when(usuarioEmpresaBrainRepository.findById(usuarioEmpresaId))
                .thenReturn(Optional.of(empresa));
        when(usuarioEmpresaBrainRepository.save(empresa)).thenReturn(empresa);
        when(negocioBrainRepository.findAllByEmpresaId(empresaId)).thenReturn(List.of());

        useCase.deactivate(usuarioEmpresaId);

        verify(usuarioNegocioBrainRepository, never())
                .findByUsuarioIdAndNegocioIdIn(any(UUID.class), anyCollection());
    }

    @Test
    void asignacionInexistenteDevuelveVacio() {
        when(usuarioEmpresaBrainRepository.findById(usuarioEmpresaId)).thenReturn(Optional.empty());

        assertThat(useCase.deactivate(usuarioEmpresaId)).isEmpty();
    }

    private NegocioBrain negocio(Integer negocioId) {
        return NegocioBrain.builder()
                .negocioId(negocioId)
                .empresaId(empresaId)
                .nombre("Negocio " + negocioId)
                .build();
    }
}
