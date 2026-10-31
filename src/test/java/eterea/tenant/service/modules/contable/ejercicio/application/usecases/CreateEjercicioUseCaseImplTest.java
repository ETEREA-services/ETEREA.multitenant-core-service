package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateEjercicioUseCaseImplTest {

    @Mock
    private EjercicioRepository repository;

    private CreateEjercicioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateEjercicioUseCaseImpl(repository);
    }

    @Test
    void creaUnEjercicioNormalizandoElNombreYSinBloquear() {
        Ejercicio nuevo = Ejercicio.builder()
                .nombre("  Ejercicio Económico 2027  ")
                .fechaInicio(LocalDate.of(2027, 1, 1))
                .fechaFin(LocalDate.of(2027, 12, 31))
                .bloqueado(true)
                .build();

        when(repository.existsSolapamiento(
                LocalDate.of(2027, 1, 1), LocalDate.of(2027, 12, 31))).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Ejercicio resultado = useCase.create(nuevo);

        assertThat(resultado.getNombre()).isEqualTo("Ejercicio Económico 2027");
        assertThat(resultado.getFechaInicio()).isEqualTo(LocalDate.of(2027, 1, 1));
        assertThat(resultado.getFechaFin()).isEqualTo(LocalDate.of(2027, 12, 31));
        assertThat(resultado.isBloqueado()).isFalse();
        verify(repository).save(any());
    }

    @Test
    void rechazaUnNombreVacio() {
        Ejercicio nuevo = nuevo("   ");

        assertThatThrownBy(() -> useCase.create(nuevo))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NOMBRE_REQUERIDO"));

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaUnNombreDemasiadoLargo() {
        Ejercicio nuevo = nuevo("x".repeat(129));

        assertThatThrownBy(() -> useCase.create(nuevo))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NOMBRE_INVALIDO"));

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaFechasInvertidas() {
        Ejercicio nuevo = nuevo("Ejercicio");
        nuevo.setFechaInicio(LocalDate.of(2027, 12, 31));
        nuevo.setFechaFin(LocalDate.of(2027, 1, 1));

        assertThatThrownBy(() -> useCase.create(nuevo))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_FECHAS_INVALIDAS"));

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaUnEjercicioSolapado() {
        Ejercicio nuevo = nuevo("Ejercicio");
        when(repository.existsSolapamiento(
                nuevo.getFechaInicio(), nuevo.getFechaFin())).thenReturn(true);

        assertThatThrownBy(() -> useCase.create(nuevo))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_SOLAPADO"));

        verify(repository, never()).save(any());
    }

    private static Ejercicio nuevo(String nombre) {
        return Ejercicio.builder()
                .nombre(nombre)
                .fechaInicio(LocalDate.of(2027, 1, 1))
                .fechaFin(LocalDate.of(2027, 12, 31))
                .build();
    }

}
