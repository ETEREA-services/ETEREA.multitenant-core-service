package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.NegocioInfoRepository;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.UsuarioNegocioBrainRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUsuarioNegocioBrainsByUsuarioIdUseCaseImplTest {

    @Mock
    private UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;

    @Mock
    private NegocioInfoRepository negocioInfoRepository;

    private GetUsuarioNegocioBrainsByUsuarioIdUseCaseImpl useCase;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        useCase = new GetUsuarioNegocioBrainsByUsuarioIdUseCaseImpl(
                usuarioNegocioBrainRepository, negocioInfoRepository);
        usuarioId = UUID.randomUUID();
    }

    @Test
    void devuelveTodosLosNegociosAsignadosConSuEmpresa() {
        UUID empresaId = UUID.randomUUID();
        UsuarioNegocioBrain asignacion = asignacion(10);
        NegocioInfo negocio = negocio(10, empresaId);

        when(usuarioNegocioBrainRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of(asignacion));
        when(negocioInfoRepository.findAllByIds(List.of(10))).thenReturn(List.of(negocio));

        var resultado = useCase.getByUsuarioId(usuarioId);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getFirst().getNegocioId()).isEqualTo(10);
        assertThat(resultado.getFirst().getNegocio().getEmpresaId()).isEqualTo(empresaId);
        assertThat(resultado.getFirst().getActivo()).isTrue();
    }

    @Test
    void ignoraAsignacionesCuyoNegocioYaNoExiste() {
        when(usuarioNegocioBrainRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of(asignacion(99)));
        when(negocioInfoRepository.findAllByIds(List.of(99))).thenReturn(List.of());

        assertThat(useCase.getByUsuarioId(usuarioId)).isEmpty();
    }

    @Test
    void resuelveTodasLasAsignacionesConUnaSolaConsulta() {
        UsuarioNegocioBrain primera = asignacion(10);
        UsuarioNegocioBrain segunda = asignacion(20);

        when(usuarioNegocioBrainRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of(primera, segunda));
        when(negocioInfoRepository.findAllByIds(List.of(10, 20)))
                .thenReturn(List.of(negocio(10, UUID.randomUUID()), negocio(20, UUID.randomUUID())));

        var resultado = useCase.getByUsuarioId(usuarioId);

        assertThat(resultado).hasSize(2);
        verify(negocioInfoRepository).findAllByIds(List.of(10, 20));
        verify(negocioInfoRepository, never()).findAllByEmpresaId(any());
    }

    @Test
    void noConsultaNegociosSiNoHayAsignaciones() {
        when(usuarioNegocioBrainRepository.findByUsuarioId(usuarioId)).thenReturn(List.of());

        assertThat(useCase.getByUsuarioId(usuarioId)).isEmpty();

        verify(negocioInfoRepository, never()).findAllByIds(any());
    }

    private UsuarioNegocioBrain asignacion(Integer negocioId) {
        return UsuarioNegocioBrain.builder()
                .usuarioNegocioId(UUID.randomUUID())
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .activo(true)
                .build();
    }

    private NegocioInfo negocio(Integer negocioId, UUID empresaId) {
        return NegocioInfo.builder()
                .negocioId(negocioId)
                .empresaId(empresaId)
                .nombre("Negocio " + negocioId)
                .tenantId("tenant-" + negocioId)
                .build();
    }
}
