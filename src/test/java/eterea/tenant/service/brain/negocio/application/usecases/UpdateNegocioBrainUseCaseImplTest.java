package eterea.tenant.service.brain.negocio.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateNegocioBrainUseCaseImplTest {

    @Mock
    private NegocioBrainRepository negocioBrainRepository;

    private UpdateNegocioBrainUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateNegocioBrainUseCaseImpl(negocioBrainRepository);
    }

    @Test
    void conservaTenantIdYPasswordSiNoSeEnvian() {
        NegocioBrain existente = NegocioBrain.builder()
                .negocioId(1)
                .empresaId(UUID.randomUUID())
                .nombre("Anterior")
                .tenantId("TSA_tenant_viejo")
                .databasePassword("secreto")
                .build();
        NegocioBrain cambios = NegocioBrain.builder()
                .empresaId(existente.getEmpresaId())
                .nombre("Nuevo")
                .tenantId(null)
                .databasePassword(null)
                .build();

        when(negocioBrainRepository.findById(1)).thenReturn(Optional.of(existente));
        when(negocioBrainRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = useCase.update(1, cambios);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Nuevo");
        assertThat(resultado.get().getTenantId()).isEqualTo("TSA_tenant_viejo");
        assertThat(resultado.get().getDatabasePassword()).isEqualTo("secreto");
    }

    @Test
    void actualizaTenantIdYPasswordSiSeEnvian() {
        NegocioBrain existente = NegocioBrain.builder()
                .negocioId(1)
                .empresaId(UUID.randomUUID())
                .nombre("Anterior")
                .tenantId("TSA_tenant_viejo")
                .databasePassword("secreto")
                .build();
        NegocioBrain cambios = NegocioBrain.builder()
                .empresaId(existente.getEmpresaId())
                .nombre("Nuevo")
                .tenantId("TSA_tenant_nuevo")
                .databasePassword("nueva")
                .build();

        when(negocioBrainRepository.findById(1)).thenReturn(Optional.of(existente));
        when(negocioBrainRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = useCase.update(1, cambios);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTenantId()).isEqualTo("TSA_tenant_nuevo");
        assertThat(resultado.get().getDatabasePassword()).isEqualTo("nueva");
    }
}
