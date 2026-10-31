package eterea.tenant.service.modules.contable.coeficienteinflacion.application.usecases;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.exception.CoeficienteInflacionInvalidaException;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacion;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.out.CoeficienteInflacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCoeficientesAnioUseCaseImplTest {

    @Mock
    private CoeficienteInflacionRepository repository;

    private FindCoeficientesAnioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindCoeficientesAnioUseCaseImpl(repository);
    }

    @Test
    void devuelveDoceMesesConNullEnLosFaltantes() {
        when(repository.findAllByAnho(2026)).thenReturn(List.of(
                registro(1, "1.234500"),
                registro(6, "2.000000")));

        CoeficienteInflacionAnio anio = useCase.find(2026);

        assertThat(anio.getAnho()).isEqualTo(2026);
        assertThat(anio.getMeses()).hasSize(12);
        assertThat(anio.getMeses().get(0).coeficiente()).isEqualByComparingTo("1.234500");
        assertThat(anio.getMeses().get(0).cargado()).isTrue();
        assertThat(anio.getMeses().get(1).coeficiente()).isNull();
        assertThat(anio.getMeses().get(1).cargado()).isFalse();
        assertThat(anio.getMeses().get(5).coeficiente()).isEqualByComparingTo("2.000000");
        assertThat(anio.cargados()).isEqualTo(2);
        assertThat(anio.total()).isEqualTo(12);
    }

    @Test
    void devuelveUnAnioVacioCuandoNoHayRegistros() {
        when(repository.findAllByAnho(2026)).thenReturn(List.of());

        CoeficienteInflacionAnio anio = useCase.find(2026);

        assertThat(anio.getMeses()).hasSize(12);
        assertThat(anio.cargados()).isZero();
        assertThat(anio.getMeses()).allSatisfy(mes -> assertThat(mes.coeficiente()).isNull());
    }

    @Test
    void anteDuplicadosLegacyPrefiereElValorNoNuloYElIdMasAlto() {
        when(repository.findAllByAnho(2026)).thenReturn(List.of(
                registro(1, "0.000000", 1L),
                registro(1, "1.500000", 2L),
                registro(2, "2.000000", 3L),
                registro(2, "2.100000", 4L),
                registroSinValor(3, 5L),
                registro(3, "3.300000", 6L)));

        CoeficienteInflacionAnio anio = useCase.find(2026);

        assertThat(anio.getMeses().get(0).coeficiente()).isEqualByComparingTo("1.500000");
        assertThat(anio.getMeses().get(0).coeficienteInflacionId()).isEqualTo(2L);
        assertThat(anio.getMeses().get(1).coeficiente()).isEqualByComparingTo("2.100000");
        assertThat(anio.getMeses().get(1).coeficienteInflacionId()).isEqualTo(4L);
        assertThat(anio.getMeses().get(2).coeficiente()).isEqualByComparingTo("3.300000");
        assertThat(anio.getMeses().get(2).coeficienteInflacionId()).isEqualTo(6L);
    }

    @Test
    void rechazaUnAnioInvalido() {
        assertThatThrownBy(() -> useCase.find(1800))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_ANIO_INVALIDO"));
    }

    private static CoeficienteInflacion registro(int mes, String coeficiente) {
        return registro(mes, coeficiente, (long) mes);
    }

    private static CoeficienteInflacion registro(int mes, String coeficiente, Long id) {
        return CoeficienteInflacion.builder()
                .coeficienteInflacionId(id)
                .anho(2026)
                .mes(mes)
                .coeficiente(new BigDecimal(coeficiente))
                .build();
    }

    private static CoeficienteInflacion registroSinValor(int mes, Long id) {
        return CoeficienteInflacion.builder()
                .coeficienteInflacionId(id)
                .anho(2026)
                .mes(mes)
                .coeficiente(null)
                .build();
    }

}
