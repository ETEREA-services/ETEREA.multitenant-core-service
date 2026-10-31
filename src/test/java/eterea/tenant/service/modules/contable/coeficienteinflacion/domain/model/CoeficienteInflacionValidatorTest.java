package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.exception.CoeficienteInflacionInvalidaException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoeficienteInflacionValidatorTest {

    @Test
    void aceptaLosLimitesDelAnio() {
        CoeficienteInflacionValidator.validarAnio(1900);
        CoeficienteInflacionValidator.validarAnio(2100);
    }

    @Test
    void rechazaUnAnioFueraDeRango() {
        assertThatThrownBy(() -> CoeficienteInflacionValidator.validarAnio(1899))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_ANIO_INVALIDO"));

        assertThatThrownBy(() -> CoeficienteInflacionValidator.validarAnio(2101))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_ANIO_INVALIDO"));
    }

    @Test
    void rechazaUnMesFueraDeRango() {
        assertThatThrownBy(() -> CoeficienteInflacionValidator.validarMes(0))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_MES_INVALIDO"));

        assertThatThrownBy(() -> CoeficienteInflacionValidator.validarMes(13))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_MES_INVALIDO"));
    }

    @Test
    void normalizaElCoeficienteASeisDecimales() {
        BigDecimal normalizado = CoeficienteInflacionValidator.normalizar(
                new BigDecimal("1.2345"));

        assertThat(normalizado).isEqualByComparingTo("1.234500");
        assertThat(normalizado.scale()).isEqualTo(6);
    }

    @Test
    void aceptaCeroYRechazaMasDeSeisDecimalesSignificativos() {
        assertThat(CoeficienteInflacionValidator.normalizar(BigDecimal.ZERO))
                .isEqualByComparingTo("0.000000");

        assertThat(CoeficienteInflacionValidator.normalizar(
                new BigDecimal("1.2300000000")))
                .isEqualByComparingTo("1.230000");

        assertThatThrownBy(() -> CoeficienteInflacionValidator.normalizar(
                new BigDecimal("1.1234567")))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo())
                                .isEqualTo("COEFICIENTE_PRECISION_INVALIDA"));
    }

    @Test
    void rechazaUnCoeficienteNegativo() {
        assertThatThrownBy(() -> CoeficienteInflacionValidator.normalizar(
                new BigDecimal("-0.01")))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_VALOR_INVALIDO"));
    }

    @Test
    void aceptaNullParaLimpiarUnMes() {
        assertThat(CoeficienteInflacionValidator.normalizar(null)).isNull();
    }

    @Test
    void rechazaMesesDuplicadosEnElPayload() {
        List<CoeficienteMensualCambio> cambios = List.of(
                new CoeficienteMensualCambio(1, new BigDecimal("1.1")),
                new CoeficienteMensualCambio(1, new BigDecimal("1.2")));

        assertThatThrownBy(() -> CoeficienteInflacionValidator.validarCambios(cambios))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_MES_DUPLICADO"));
    }

    @Test
    void rechazaUnPayloadNulo() {
        assertThatThrownBy(() -> CoeficienteInflacionValidator.validarCambios(null))
                .isInstanceOfSatisfying(CoeficienteInflacionInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("COEFICIENTE_SIN_CAMBIOS"));
    }

}
