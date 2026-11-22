package eterea.tenant.service.brain.usuarios.usuario_negocio.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.NegocioInfo;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioBrain;
import eterea.tenant.service.brain.usuarios.usuario_negocio.domain.model.UsuarioNegocioConNegocioBrain;
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
class GetUsuarioNegocioBrainsByUsuarioIdAndEmpresaIdUseCaseImplTest {

    @Mock
    private UsuarioNegocioBrainRepository usuarioNegocioBrainRepository;

    @Mock
    private NegocioInfoRepository negocioInfoRepository;

    private GetUsuarioNegocioBrainsByUsuarioIdAndEmpresaIdUseCaseImpl useCase;
    private UUID usuarioId;
    private UUID empresaId;

    @BeforeEach
    void setUp() {
        useCase = new GetUsuarioNegocioBrainsByUsuarioIdAndEmpresaIdUseCaseImpl(
                usuarioNegocioBrainRepository, negocioInfoRepository);
        usuarioId = UUID.randomUUID();
        empresaId = UUID.randomUUID();
    }

    @Test
    void resuelveLosNegociosDeLaEmpresaConUnaSolaConsulta() {
        UsuarioNegocioBrain propio = asignacion(10);
        UsuarioNegocioBrain ajeno = asignacion(20);
        UsuarioNegocioBrain inexistente = asignacion(99);

        when(usuarioNegocioBrainRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of(propio, ajeno, inexistente));
        when(negocioInfoRepository.findAllByEmpresaId(empresaId))
                .thenReturn(List.of(negocio(10, empresaId), negocio(20, empresaId)));

        var resultado = useCase.getByUsuarioIdAndEmpresaId(usuarioId, empresaId);

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(UsuarioNegocioConNegocioBrain::getNegocioId)
                .containsExactly(10, 20);
        assertThat(resultado).allSatisfy(asociacion ->
                assertThat(asociacion.getNegocio().getEmpresaId()).isEqualTo(empresaId));

        verify(negocioInfoRepository).findAllByEmpresaId(empresaId);
        verifyNoMoreInteractions(negocioInfoRepository);
    }

    @Test
    void devuelveVacioSiLaEmpresaNoTieneNegocios() {
        when(usuarioNegocioBrainRepository.findByUsuarioId(usuarioId))
                .thenReturn(List.of(asignacion(10)));
        when(negocioInfoRepository.findAllByEmpresaId(empresaId)).thenReturn(List.of());

        assertThat(useCase.getByUsuarioIdAndEmpresaId(usuarioId, empresaId)).isEmpty();
    }

    private UsuarioNegocioBrain asignacion(Integer negocioId) {
        return UsuarioNegocioBrain.builder()
                .usuarioNegocioId(UUID.randomUUID())
                .usuarioId(usuarioId)
                .negocioId(negocioId)
                .activo(true)
                .build();
    }

    private NegocioInfo negocio(Integer negocioId, UUID empresa) {
        return NegocioInfo.builder()
                .negocioId(negocioId)
                .empresaId(empresa)
                .nombre("Negocio " + negocioId)
                .tenantId("tenant-" + negocioId)
                .build();
    }
}
