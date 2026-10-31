package eterea.tenant.service.modules.contable.cuenta.domain.model;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CodigoCuentaTest {

    @ParameterizedTest(name = "{0} -> nivel {1}, padre {2}")
    @CsvSource({
            "10000000, 1, 0",
            "11000000, 2, 10000000",
            "11100000, 3, 11000000",
            "11101000, 4, 11100000",
            "11101001, 5, 11101000",
            "12000000, 2, 10000000",
            "12300000, 3, 12000000",
            "12340000, 4, 12300000",
            "99999999, 5, 99999000"
    })
    void calculaNivelYPadre(long valor, int nivel, long padre) {
        CodigoCuenta codigo = CodigoCuenta.of(valor);

        assertThat(codigo.nivel()).isEqualTo(nivel);
        assertThat(codigo.padre()).isEqualTo(padre);
        assertThat(codigo.esRaiz()).isEqualTo(padre == 0L);
    }

    @ParameterizedTest(name = "{0} -> grados {1}/{2}/{3}/{4}")
    @CsvSource({
            "10000000, 10000000, 10000000, 10000000, 10000000",
            "11000000, 10000000, 11000000, 11000000, 11000000",
            "11100000, 10000000, 11000000, 11100000, 11100000",
            "11101000, 10000000, 11000000, 11100000, 11101000",
            "11101001, 10000000, 11000000, 11100000, 11101000",
            "12000000, 10000000, 12000000, 12000000, 12000000",
            "12300000, 10000000, 12000000, 12300000, 12300000",
            "12340000, 10000000, 12000000, 12300000, 12340000"
    })
    void calculaGrados(long valor, long grado1, long grado2, long grado3, long grado4) {
        CodigoCuenta codigo = CodigoCuenta.of(valor);

        assertThat(codigo.grado1()).isEqualTo(grado1);
        assertThat(codigo.grado2()).isEqualTo(grado2);
        assertThat(codigo.grado3()).isEqualTo(grado3);
        assertThat(codigo.grado4()).isEqualTo(grado4);
    }

    @Test
    void codigosCortosSonRaizYNivelCinco() {
        assertThat(CodigoCuenta.of(1L).nivel()).isEqualTo(5);
        assertThat(CodigoCuenta.of(1L).esRaiz()).isTrue();

        assertThat(CodigoCuenta.of(1100L).nivel()).isEqualTo(5);
        assertThat(CodigoCuenta.of(1100L).esRaiz()).isTrue();

        assertThat(CodigoCuenta.of(9_999_999L).nivel()).isEqualTo(5);
        assertThat(CodigoCuenta.of(9_999_999L).esRaiz()).isTrue();
    }

    @Test
    void codigosCortosConCerosFinalesConservanElGradoDelLegacy() {
        CodigoCuenta mil = CodigoCuenta.of(1_000L);
        assertThat(mil.esRaiz()).isTrue();
        assertThat(mil.nivel()).isEqualTo(4);

        CodigoCuenta millon = CodigoCuenta.of(1_000_000L);
        assertThat(millon.esRaiz()).isTrue();
        assertThat(millon.nivel()).isEqualTo(2);
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @MethodSource("casosDeAncestros")
    void calculaAncestros(long valor, List<Long> esperados) {
        assertThat(CodigoCuenta.of(valor).ancestros()).containsExactlyElementsOf(esperados);
    }

    static Stream<Arguments> casosDeAncestros() {
        return Stream.of(
                arguments(11_101_001L, List.of(11_101_000L, 11_100_000L, 11_000_000L, 10_000_000L)),
                arguments(11_101_000L, List.of(11_100_000L, 11_000_000L, 10_000_000L)),
                arguments(11_100_000L, List.of(11_000_000L, 10_000_000L)),
                arguments(11_000_000L, List.of(10_000_000L)),
                arguments(10_000_000L, List.of()),
                arguments(1_100L, List.of(11_000_000L, 10_000_000L)),
                arguments(1_234L, List.of(12_340_000L, 12_300_000L, 12_000_000L, 10_000_000L)),
                arguments(1L, List.of(10_000_000L)),
                arguments(9_999_999L, List.of(99_999_000L, 99_900_000L, 99_000_000L, 90_000_000L))
        );
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "1, 10000000",
            "1234, 12340000",
            "9999999, 99999990",
            "10000000, 10000000",
            "11101001, 11101001",
            "99999999, 99999999"
    })
    void calculaValorParaAncestros(long valor, long esperado) {
        assertThat(CodigoCuenta.of(valor).valorParaAncestros()).isEqualTo(esperado);
    }

    @ParameterizedTest(name = "{0} -> limite {1}")
    @CsvSource({
            "10000000, 19999999",
            "11000000, 11999999",
            "11100000, 11199999",
            "11101000, 11101999",
            "11101001, 11101001",
            "1100, 11999999",
            "1234, 12349999",
            "99999999, 99999999"
    })
    void calculaLimiteSuperiorDelSubarbol(long valor, long esperado) {
        assertThat(CodigoCuenta.of(valor).limiteSuperior()).isEqualTo(esperado);
    }

    @Test
    void aceptaTextoNumericoConCerosALaIzquierda() {
        assertThat(CodigoCuenta.of("00000001")).isEqualTo(CodigoCuenta.of(1L));
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L, 100_000_000L})
    void rechazaValoresFueraDeRango(long valor) {
        assertThatThrownBy(() -> CodigoCuenta.of(valor))
                .isInstanceOfSatisfying(CuentaInvalidaException.class, ex -> {
                    assertThat(ex.getCodigo()).isEqualTo("CUENTA_CODIGO_INVALIDO");
                    assertThat(ex).hasMessageContaining("código");
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "12a", "123456789", "-1", "1.5", " 123"})
    void rechazaTextoInvalido(String valor) {
        assertThatThrownBy(() -> CodigoCuenta.of(valor))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_CODIGO_INVALIDO"));
    }

    @Test
    void rechazaTextoNulo() {
        assertThatThrownBy(() -> CodigoCuenta.of((String) null))
                .isInstanceOfSatisfying(CuentaInvalidaException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("CUENTA_CODIGO_INVALIDO"));
    }

    @Test
    void usaElValorComoIdentidad() {
        assertThat(CodigoCuenta.of(11_101_001L)).isEqualTo(CodigoCuenta.of(11_101_001L));
        assertThat(CodigoCuenta.of(11_101_001L)).hasSameHashCodeAs(CodigoCuenta.of(11_101_001L));
        assertThat(CodigoCuenta.of(11_101_001L)).hasToString("11101001");
    }
}
