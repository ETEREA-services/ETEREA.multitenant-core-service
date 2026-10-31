package eterea.tenant.service.brain.negocio.application.usecases;

import eterea.tenant.service.modules.platform.negocio.domain.model.Negocio;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetNegocioBrainsByEmpresaIdUseCaseImplTest {

    @Mock
    private NegocioBrainRepository negocioBrainRepository;

    private GetNegocioBrainsByEmpresaIdUseCaseImpl useCase;
    private UUID empresaId;

    @BeforeEach
    void setUp() {
        useCase = new GetNegocioBrainsByEmpresaIdUseCaseImpl(negocioBrainRepository);
        empresaId = UUID.randomUUID();
    }

    @Test
    void devuelveSoloLosNegociosDeLaEmpresa() {
        NegocioBrain negocio = NegocioBrain.builder()
                .negocioId(10)
                .empresaId(empresaId)
                .nombre("Negocio")
                .tenantId("tenant-1")
                .build();
        when(negocioBrainRepository.findAllByEmpresaId(empresaId)).thenReturn(List.of(negocio));

        var resultado = useCase.getByEmpresaId(empresaId);

        assertThat(resultado).containsExactly(negocio);
    }
}
