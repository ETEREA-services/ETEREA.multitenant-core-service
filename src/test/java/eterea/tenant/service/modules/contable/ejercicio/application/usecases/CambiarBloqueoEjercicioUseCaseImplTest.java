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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CambiarBloqueoEjercicioUseCaseImplTest {

    @Mock
    private EjercicioRepository repository;

    private CambiarBloqueoEjercicioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CambiarBloqueoEjercicioUseCaseImpl(repository);
    }

    @Test
    void bloqueaUnEjercicio() {
        Ejercicio ejercicio = ejercicio(false);
        when(repository.findById(1L)).thenReturn(Optional.of(ejercicio));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Ejercicio resultado = useCase.cambiarBloqueo(1L, true);

        assertThat(resultado.isBloqueado()).isTrue();
        verify(repository).save(ejercicio);
    }

    @Test
    void desbloqueaUnEjercicio() {
        Ejercicio ejercicio = ejercicio(true);
        when(repository.findById(1L)).thenReturn(Optional.of(ejercicio));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Ejercicio resultado = useCase.cambiarBloqueo(1L, false);

        assertThat(resultado.isBloqueado()).isFalse();
        verify(repository).save(ejercicio);
    }

    @Test
    void esIdempotenteSiElEstadoNoCambia() {
        Ejercicio ejercicio = ejercicio(true);
        when(repository.findById(1L)).thenReturn(Optional.of(ejercicio));

        Ejercicio resultado = useCase.cambiarBloqueo(1L, true);

        assertThat(resultado.isBloqueado()).isTrue();
        verify(repository, never()).save(any());
    }

    @Test
    void rechazaUnEjercicioInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.cambiarBloqueo(1L, true))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NO_EXISTE"));
    }

    private static Ejercicio ejercicio(boolean bloqueado) {
        return Ejercicio.builder()
                .ejercicioId(1L)
                .nombre("Ejercicio")
                .fechaInicio(LocalDate.now().plusDays(30))
                .fechaFin(LocalDate.now().plusYears(1))
                .bloqueado(bloqueado)
                .build();
    }

}
