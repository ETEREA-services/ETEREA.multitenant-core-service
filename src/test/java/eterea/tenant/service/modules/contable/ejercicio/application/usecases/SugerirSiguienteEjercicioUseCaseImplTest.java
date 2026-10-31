package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioSugerido;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SugerirSiguienteEjercicioUseCaseImplTest {

    @Mock
    private EjercicioRepository repository;

    private SugerirSiguienteEjercicioUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new SugerirSiguienteEjercicioUseCaseImpl(repository);
    }

    @Test
    void sinEjerciciosProponeElAnioCalendarioActual() {
        when(repository.findUltimo()).thenReturn(Optional.empty());
        int anio = LocalDate.now().getYear();

        EjercicioSugerido sugerido = useCase.sugerir();

        assertThat(sugerido.disponible()).isTrue();
        assertThat(sugerido.nombre()).isEqualTo("Ejercicio Económico " + anio);
        assertThat(sugerido.fechaInicio()).isEqualTo(LocalDate.of(anio, 1, 1));
        assertThat(sugerido.fechaFin()).isEqualTo(LocalDate.of(anio, 12, 31));
        assertThat(sugerido.origen()).isEqualTo("SIN_EJERCICIOS");
        assertThat(sugerido.motivo()).isNull();
    }

    @Test
    void noProponeNadaSiElUltimoEmpiezaEnElAnioActual() {
        int anio = LocalDate.now().getYear();
        when(repository.findUltimo()).thenReturn(Optional.of(ejercicio(
                LocalDate.of(anio, 1, 1), LocalDate.of(anio, 12, 31))));

        EjercicioSugerido sugerido = useCase.sugerir();

        assertThat(sugerido.disponible()).isFalse();
        assertThat(sugerido.origen()).isEqualTo("ULTIMO_EJERCICIO");
        assertThat(sugerido.motivo()).contains("año actual o futuro");
    }

    @Test
    void noProponeNadaSiElUltimoEsFuturo() {
        LocalDate inicio = LocalDate.now().plusYears(1).withDayOfYear(1);
        when(repository.findUltimo()).thenReturn(Optional.of(ejercicio(
                inicio, inicio.plusYears(1).minusDays(1))));

        EjercicioSugerido sugerido = useCase.sugerir();

        assertThat(sugerido.disponible()).isFalse();
        assertThat(sugerido.motivo()).contains("año actual o futuro");
    }

    @Test
    void proponeElSiguienteCuandoElUltimoYaTermino() {
        LocalDate fin = LocalDate.now().minusYears(1).withMonth(12).withDayOfMonth(31);
        LocalDate inicio = fin.minusYears(1).plusDays(1);
        when(repository.findUltimo()).thenReturn(Optional.of(ejercicio(inicio, fin)));

        EjercicioSugerido sugerido = useCase.sugerir();

        assertThat(sugerido.disponible()).isTrue();
        assertThat(sugerido.fechaInicio()).isEqualTo(fin.plusDays(1));
        assertThat(sugerido.fechaFin()).isEqualTo(fin.plusYears(1));
        assertThat(sugerido.nombre())
                .isEqualTo("Ejercicio Económico " + fin.plusYears(1).getYear());
        assertThat(sugerido.motivo()).isNull();
    }

    @Test
    void ajustaEl29DeFebreroAl28DelAnioSiguiente() {
        LocalDate inicio = LocalDate.of(2024, 3, 1);
        LocalDate fin = LocalDate.of(2028, 2, 29);
        when(repository.findUltimo()).thenReturn(Optional.of(ejercicio(inicio, fin)));

        EjercicioSugerido sugerido = useCase.sugerir();

        assertThat(sugerido.disponible()).isTrue();
        assertThat(sugerido.fechaInicio()).isEqualTo(LocalDate.of(2028, 3, 1));
        assertThat(sugerido.fechaFin()).isEqualTo(LocalDate.of(2029, 2, 28));
    }

    private static Ejercicio ejercicio(LocalDate inicio, LocalDate fin) {
        return Ejercicio.builder()
                .ejercicioId(1L)
                .nombre("Ejercicio")
                .fechaInicio(inicio)
                .fechaFin(fin)
                .build();
    }

}
