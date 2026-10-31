package eterea.tenant.service.modules.contable.coeficienteinflacion.application.usecases;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.exception.CoeficienteInflacionInvalidaException;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacion;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteMensualCambio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.out.CoeficienteInflacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveCoeficientesAnioUseCaseImplTest {

    @Mock
    private CoeficienteInflacionRepository repository;

    private SaveCoeficientesAnioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveCoeficientesAnioUseCaseImpl(repository);
    }

    @Test
    void creaUnMesNuevoNormalizado() {
        when(repository.findByAnhoAndMes(2026, 1)).thenReturn(Optional.empty());
        when(repository.findAllByAnho(2026)).thenReturn(List.of(
                registro(1, "1.234500")));

        CoeficienteInflacionAnio resultado = useCase.save(2026, List.of(
                new CoeficienteMensualCambio(1, new BigDecimal("1.2345"))));

        ArgumentCaptor<CoeficienteInflacion> captor =
                ArgumentCaptor.forClass(CoeficienteInflacion.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getAnho()).isEqualTo(2026);
        assertThat(captor.getValue().getMes()).isEqualTo(1);
        assertThat(captor.getValue().getCoeficiente()).isEqualByComparingTo("1.234500");
        assertThat(resultado.cargados()).isEqualTo(1);
    }

    @Test
    void actualizaUnMesExistente() {
        CoeficienteInflacion existente = registro(2, "1.000000");
        when(repository.findByAnhoAndMes(2026, 2)).thenReturn(Optional.of(existente));
        when(repository.findAllByAnho(2026)).thenReturn(List.of(
                registro(2, "1.500000")));

        useCase.save(2026, List.of(
                new CoeficienteMensualCambio(2, new BigDecimal("1.5"))));

        ArgumentCaptor<CoeficienteInflacion> captor =
                ArgumentCaptor.forClass(CoeficienteInflacion.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getCoeficiente()).isEqualByComparingTo("1.500000");
    }

    @Test
    void noGuardaSiElValorNoCambio() {
        CoeficienteInflacion existente = registro(2, "1.500000");
        when(repository.findByAnhoAndMes(2026, 2)).thenReturn(Optional.of(existente));
        when(repository.findAllByAnho(2026)).thenReturn(List.of(existente));

        useCase.save(2026, List.of(
                new CoeficienteMensualCambio(2, new BigDecimal("1.500000"))));

        verify(repository, never()).save(any());
    }

    @Test
    void limpiaUnMesExistente() {
        CoeficienteInflacion existente = registro(3, "1.000000");
        when(repository.findByAnhoAndMes(2026, 3)).thenReturn(Optional.of(existente));
        when(repository.findAllByAnho(2026)).thenReturn(List.of());

        useCase.save(2026, List.of(
                new CoeficienteMensualCambio(3, null)));

        verify(repository).deleteById(3L);
        verify(repository, never()).save(any());
    }

    @Test
    void rechazaMesesDuplicadosSinTocarLaBase() {
        assertThatThrownBy(() -> useCase.save(2026, List.of(
                new CoeficienteMensualCambio(1, new BigDecimal("1.1")),
                new CoeficienteMensualCambio(1, new BigDecimal("1.2")))))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_MES_DUPLICADO"));

        verify(repository, never()).save(any());
        verify(repository, never()).deleteById(any());
    }

    @Test
    void rechazaUnCoeficienteNegativo() {
        assertThatThrownBy(() -> useCase.save(2026, List.of(
                new CoeficienteMensualCambio(1, new BigDecimal("-1")))))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_VALOR_INVALIDO"));

        verify(repository, never()).save(any());
    }

    @Test
    void unPayloadVacioEsUnNoOp() {
        when(repository.findAllByAnho(2026)).thenReturn(List.of());

        CoeficienteInflacionAnio resultado = useCase.save(2026, List.of());

        assertThat(resultado.getMeses()).hasSize(12);
        assertThat(resultado.cargados()).isZero();
        verify(repository, never()).save(any());
        verify(repository, never()).deleteById(any());
    }

    private static CoeficienteInflacion registro(int mes, String coeficiente) {
        return CoeficienteInflacion.builder()
                .coeficienteInflacionId((long) mes)
                .anho(2026)
                .mes(mes)
                .coeficiente(new BigDecimal(coeficiente))
                .build();
    }

}
