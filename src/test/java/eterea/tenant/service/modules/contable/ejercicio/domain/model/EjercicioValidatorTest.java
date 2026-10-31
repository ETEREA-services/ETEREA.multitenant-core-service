package eterea.tenant.service.modules.contable.ejercicio.domain.model;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EjercicioValidatorTest {

    @Test
    void normalizaElNombre() {
        assertThat(EjercicioValidator.normalizarNombre("  Ejercicio 2027  "))
                .isEqualTo("Ejercicio 2027");
    }

    @Test
    void rechazaUnNombreVacio() {
        assertThatThrownBy(() -> EjercicioValidator.normalizarNombre("   "))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NOMBRE_REQUERIDO"));
    }

    @Test
    void rechazaUnNombreDemasiadoLargo() {
        assertThatThrownBy(() -> EjercicioValidator.normalizarNombre("x".repeat(129)))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NOMBRE_INVALIDO"));
    }

    @Test
    void aceptaUnNombreEnElLimite() {
        assertThatCode(() -> EjercicioValidator.normalizarNombre("x".repeat(128)))
                .doesNotThrowAnyException();
    }

    @Test
    void rechazaFechasNulas() {
        assertThatThrownBy(() -> EjercicioValidator.validarFechas(null, LocalDate.now()))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_FECHAS_REQUERIDAS"));
    }

    @Test
    void rechazaFechasInvertidas() {
        assertThatThrownBy(() -> EjercicioValidator.validarFechas(
                LocalDate.of(2027, 12, 31), LocalDate.of(2027, 1, 1)))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_FECHAS_INVALIDAS"));
    }

    @Test
    void aceptaElMismoDiaComoInicioYFin() {
        assertThatCode(() -> EjercicioValidator.validarFechas(
                LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 1)))
                .doesNotThrowAnyException();
    }

    @Test
    void validaElId() {
        assertThatThrownBy(() -> EjercicioValidator.validarId(null))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NO_EXISTE"));
        assertThatThrownBy(() -> EjercicioValidator.validarId(0L))
                .isInstanceOf(EjercicioInvalidoException.class);
        assertThatCode(() -> EjercicioValidator.validarId(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void calculaElEstadoSegunLaFecha() {
        LocalDate hoy = LocalDate.of(2026, 10, 31);
        assertThat(EstadoEjercicio.de(
                LocalDate.of(2027, 1, 1), LocalDate.of(2027, 12, 31), hoy))
                .isEqualTo(EstadoEjercicio.FUTURO);
        assertThat(EstadoEjercicio.de(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31), hoy))
                .isEqualTo(EstadoEjercicio.EN_CURSO);
        assertThat(EstadoEjercicio.de(
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), hoy))
                .isEqualTo(EstadoEjercicio.FINALIZADO);
    }

}
