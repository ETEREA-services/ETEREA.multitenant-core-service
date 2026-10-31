package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioSugerido;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.SugerirSiguienteEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Replica en el backend el cálculo que hacía {@code clsCtlEjercicios.add}.
 *
 * <p>No persiste nada: devuelve una propuesta para que el usuario la confirme.
 * Si la tabla está vacía, propone el año calendario actual; el legacy abortaba
 * silenciosamente en ese caso.</p>
 */
@Component
public class SugerirSiguienteEjercicioUseCaseImpl implements SugerirSiguienteEjercicioUseCase {

    private static final String ORIGEN_SIN_EJERCICIOS = "SIN_EJERCICIOS";
    private static final String ORIGEN_ULTIMO_EJERCICIO = "ULTIMO_EJERCICIO";

    private final EjercicioRepository repository;

    public SugerirSiguienteEjercicioUseCaseImpl(EjercicioRepository repository) {
        this.repository = repository;
    }

    @Override
    public EjercicioSugerido sugerir() {
        LocalDate hoy = LocalDate.now();
        Optional<Ejercicio> ultimo = repository.findUltimo();

        if (ultimo.isEmpty()) {
            int anio = hoy.getYear();
            return new EjercicioSugerido(
                    true,
                    "Ejercicio Económico " + anio,
                    LocalDate.of(anio, 1, 1),
                    LocalDate.of(anio, 12, 31),
                    ORIGEN_SIN_EJERCICIOS,
                    null);
        }

        Ejercicio ejercicio = ultimo.get();
        if (ejercicio.getFechaInicio().getYear() >= hoy.getYear()) {
            return new EjercicioSugerido(
                    false,
                    null,
                    null,
                    null,
                    ORIGEN_ULTIMO_EJERCICIO,
                    "Ya existe un ejercicio del año actual o futuro.");
        }

        LocalDate fechaInicio = ejercicio.getFechaFin().plusDays(1);
        LocalDate fechaFin = ejercicio.getFechaFin().plusYears(1);
        return new EjercicioSugerido(
                true,
                "Ejercicio Económico " + fechaFin.getYear(),
                fechaInicio,
                fechaFin,
                ORIGEN_ULTIMO_EJERCICIO,
                null);
    }

}
