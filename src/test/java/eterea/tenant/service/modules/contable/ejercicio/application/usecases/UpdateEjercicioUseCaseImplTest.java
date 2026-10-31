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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateEjercicioUseCaseImplTest {

    @Mock
    private EjercicioRepository repository;

    @Mock
    private EjercicioMovimientoPort movimientoPort;

    private UpdateEjercicioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateEjercicioUseCaseImpl(repository, movimientoPort);
    }

    @Test
    void actualizaElNombreSinConsultarMovimientosSiLasFechasNoCambian() {
        Ejercicio existente = existente();
        Ejercicio cambios = cambios("Ejercicio corregido",
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31));

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.existsSolapamientoExcluyendo(
                1L, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31))).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Ejercicio resultado = useCase.update(1L, cambios);

        assertThat(resultado.getNombre()).isEqualTo("Ejercicio corregido");
        verifyNoInteractions(movimientoPort);
    }

    @Test
    void permiteCambiarLasFechasSiNoHayMovimientos() {
        Ejercicio existente = existente();
        Ejercicio cambios = cambios("Ejercicio 2027",
                LocalDate.of(2027, 1, 1), LocalDate.of(2027, 12, 31));

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(movimientoPort.existeMovimientoEnEjercicio(1L)).thenReturn(false);
        when(movimientoPort.existeMovimientoEnRango(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31))).thenReturn(false);
        when(repository.existsSolapamientoExcluyendo(
                1L, LocalDate.of(2027, 1, 1), LocalDate.of(2027, 12, 31))).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Ejercicio resultado = useCase.update(1L, cambios);

        assertThat(resultado.getFechaInicio()).isEqualTo(LocalDate.of(2027, 1, 1));
        assertThat(resultado.getFechaFin()).isEqualTo(LocalDate.of(2027, 12, 31));
    }

    @Test
    void rechazaCambiarLasFechasSiTieneMovimientos() {
        Ejercicio existente = existente();
        Ejercicio cambios = cambios("Ejercicio 2027",
                LocalDate.of(2027, 1, 1), LocalDate.of(2027, 12, 31));

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(movimientoPort.existeMovimientoEnEjercicio(1L)).thenReturn(true);

        assertThatThrownBy(() -> useCase.update(1L, cambios))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_CON_MOVIMIENTOS"));

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaUnSolapamientoConOtroEjercicio() {
        Ejercicio existente = existente();
        Ejercicio cambios = cambios("Ejercicio 2027",
                LocalDate.of(2027, 1, 1), LocalDate.of(2027, 12, 31));

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(movimientoPort.existeMovimientoEnEjercicio(1L)).thenReturn(false);
        when(movimientoPort.existeMovimientoEnRango(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31))).thenReturn(false);
        when(repository.existsSolapamientoExcluyendo(
                1L, LocalDate.of(2027, 1, 1), LocalDate.of(2027, 12, 31))).thenReturn(true);

        assertThatThrownBy(() -> useCase.update(1L, cambios))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_SOLAPADO"));

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaUnEjercicioInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.update(1L, cambios("X",
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31))))
                .isInstanceOfSatisfying(EjercicioInvalidoException.class,
                        ex -> assertThat(ex.getCodigo()).isEqualTo("EJERCICIO_NO_EXISTE"));
    }

    private static Ejercicio existente() {
        return Ejercicio.builder()
                .ejercicioId(1L)
                .nombre("Ejercicio anterior")
                .fechaInicio(LocalDate.of(2026, 1, 1))
                .fechaFin(LocalDate.of(2026, 12, 31))
                .build();
    }

    private static Ejercicio cambios(String nombre, LocalDate inicio, LocalDate fin) {
        return Ejercicio.builder()
                .nombre(nombre)
                .fechaInicio(inicio)
                .fechaFin(fin)
                .build();
    }

}
