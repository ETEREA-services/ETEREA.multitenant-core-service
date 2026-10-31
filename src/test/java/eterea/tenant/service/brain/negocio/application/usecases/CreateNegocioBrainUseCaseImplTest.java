package eterea.tenant.service.brain.negocio.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eterea.tenant.service.brain.negocio.application.exception.NegocioBrainConflictException;
import eterea.tenant.service.brain.negocio.domain.model.NegocioBrain;
import eterea.tenant.service.brain.negocio.domain.ports.out.NegocioBrainRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateNegocioBrainUseCaseImplTest {

    private static final String TENANT_ID = "TSA_tenant_20261030_ba1bfc4e8e1624e67c7c90b23a1b4d8e7953";

    @Mock
    private NegocioBrainRepository negocioBrainRepository;

    private CreateNegocioBrainUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateNegocioBrainUseCaseImpl(negocioBrainRepository);
    }

    @Test
    void generaElIdAlCrear() {
        NegocioBrain negocio = negocio(99, TENANT_ID);
        when(negocioBrainRepository.existsByTenantId(TENANT_ID)).thenReturn(false);
        when(negocioBrainRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = useCase.create(negocio);

        assertThat(resultado.getNegocioId()).isNull();
        assertThat(resultado.getTenantId()).isEqualTo(TENANT_ID);
        verify(negocioBrainRepository).save(negocio);
    }

    @Test
    void rechazaUnTenantIdVacio() {
        assertThatThrownBy(() -> useCase.create(negocio(1, " ")))
                .isInstanceOf(IllegalArgumentException.class);

        verify(negocioBrainRepository, never()).save(any());
    }

    @Test
    void rechazaUnTenantIdDuplicado() {
        when(negocioBrainRepository.existsByTenantId(TENANT_ID)).thenReturn(true);

        assertThatThrownBy(() -> useCase.create(negocio(1, TENANT_ID)))
                .isInstanceOf(NegocioBrainConflictException.class);

        verify(negocioBrainRepository, never()).save(any());
    }

    private NegocioBrain negocio(Integer negocioId, String tenantId) {
        return NegocioBrain.builder()
                .negocioId(negocioId)
                .empresaId(UUID.randomUUID())
                .nombre("TSA Agencia")
                .tenantId(tenantId)
                .build();
    }
}
