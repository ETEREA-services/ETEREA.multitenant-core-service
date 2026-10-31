package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.ports.out.NegocioInfoRepository;
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
        UsuarioNegocioBrain asignacion = UsuarioNegocioBrain.builder()
                .usuarioNegocioId(UUID.randomUUID())
                .usuarioId(usuarioId)
                .negocioId(10)
                .activo(true)
                .build();
        NegocioInfo negocio = NegocioInfo.builder()
                .negocioId(10)
                .empresaId(empresaId)
                .nombre("Negocio")
                .tenantId("tenant-1")
                .build();

        when(usuarioNegocioBrainRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of(asignacion));
        when(negocioInfoRepository.findById(10)).thenReturn(Optional.of(negocio));

        var resultado = useCase.getByUsuarioId(usuarioId);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getFirst().getNegocioId()).isEqualTo(10);
        assertThat(resultado.getFirst().getNegocio().getEmpresaId()).isEqualTo(empresaId);
        assertThat(resultado.getFirst().getActivo()).isTrue();
    }

    @Test
    void ignoraAsignacionesCuyoNegocioYaNoExiste() {
        UsuarioNegocioBrain asignacion = UsuarioNegocioBrain.builder()
                .usuarioNegocioId(UUID.randomUUID())
                .usuarioId(usuarioId)
                .negocioId(99)
                .activo(true)
                .build();

        when(usuarioNegocioBrainRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of(asignacion));
        when(negocioInfoRepository.findById(99)).thenReturn(Optional.empty());

        assertThat(useCase.getByUsuarioId(usuarioId)).isEmpty();
    }
}
