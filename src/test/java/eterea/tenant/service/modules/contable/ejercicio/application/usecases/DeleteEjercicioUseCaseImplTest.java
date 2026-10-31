package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioMovimientoPort;
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
class DeleteEjercicioUseCaseImplTest {

    @Mock
    private EjercicioRepository repository;

    @Mock
    private EjercicioMovimientoPort movimientoPort;

    private DeleteEjercicioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteEjercicioUseCaseImpl(repository, movimientoPort);
    }

    @Test
    void eliminaElUltimoEjercicioFuturoSinMovimientos() {
        Ejercicio ejercicio = futuro(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(ejercicio));
        when(movimientoPort.existeMovimientoEnEjercicio(1L)).thenReturn(false);
        when(movimientoPort.existeMovimientoEnRango(
                ejercicio.getFechaInicio(), ejercicio.getFechaFin())).thenReturn(false);
        when(repository.findUltimo()).thenReturn(Optional.of(ejercicio));

        useCase.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void rechazaUnEjercicioInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.delete(1L))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NO_EXISTE"));

        verify(repository, never()).deleteById(any());
    }

    @Test
    void rechazaUnEjercicioConMovimientos() {
        Ejercicio ejercicio = futuro(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(ejercicio));
        when(movimientoPort.existeMovimientoEnEjercicio(1L)).thenReturn(true);

        assertThatThrownBy(() -> useCase.delete(1L))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_CON_MOVIMIENTOS"));

        verify(repository, never()).deleteById(any());
    }

    @Test
    void rechazaUnEjercicioQueNoEsElUltimo() {
        Ejercicio ejercicio = futuro(1L);
        Ejercicio ultimo = futuro(2L);
        when(repository.findById(1L)).thenReturn(Optional.of(ejercicio));
        when(movimientoPort.existeMovimientoEnEjercicio(1L)).thenReturn(false);
        when(movimientoPort.existeMovimientoEnRango(
                ejercicio.getFechaInicio(), ejercicio.getFechaFin())).thenReturn(false);
        when(repository.findUltimo()).thenReturn(Optional.of(ultimo));

        assertThatThrownBy(() -> useCase.delete(1L))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NO_ES_ULTIMO"));

        verify(repository, never()).deleteById(any());
    }

    @Test
    void rechazaUnEjercicioQueYaComenzo() {
        Ejercicio ejercicio = Ejercicio.builder()
                .ejercicioId(1L)
                .nombre("Ejercicio en curso")
                .fechaInicio(LocalDate.now().minusDays(1))
                .fechaFin(LocalDate.now().plusYears(1))
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(ejercicio));
        when(movimientoPort.existeMovimientoEnEjercicio(1L)).thenReturn(false);
        when(movimientoPort.existeMovimientoEnRango(
                ejercicio.getFechaInicio(), ejercicio.getFechaFin())).thenReturn(false);
        when(repository.findUltimo()).thenReturn(Optional.of(ejercicio));

        assertThatThrownBy(() -> useCase.delete(1L))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_YA_COMENZO"));

        verify(repository, never()).deleteById(any());
    }

    private static Ejercicio futuro(Long id) {
        return Ejercicio.builder()
                .ejercicioId(id)
                .nombre("Ejercicio futuro")
                .fechaInicio(LocalDate.now().plusDays(30))
                .fechaFin(LocalDate.now().plusYears(1))
                .build();
    }

}
