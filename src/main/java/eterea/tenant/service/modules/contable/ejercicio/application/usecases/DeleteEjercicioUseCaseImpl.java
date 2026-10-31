package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioValidator;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.DeleteEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioMovimientoPort;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class DeleteEjercicioUseCaseImpl implements DeleteEjercicioUseCase {

    private final EjercicioRepository repository;
    private final EjercicioMovimientoPort movimientoPort;

    public DeleteEjercicioUseCaseImpl(
            EjercicioRepository repository, EjercicioMovimientoPort movimientoPort) {
        this.repository = repository;
        this.movimientoPort = movimientoPort;
    }

    @Override
    @Transactional
    public void delete(Long ejercicioId) {
        EjercicioValidator.validarId(ejercicioId);
        Ejercicio ejercicio = repository.findById(ejercicioId)
                .orElseThrow(() -> new EjercicioInvalidoException(
                        "EJERCICIO_NO_EXISTE",
                        "El ejercicio " + ejercicioId + " no existe."));

        if (movimientoPort.existeMovimientoEnEjercicio(ejercicioId)
                || movimientoPort.existeMovimientoEnRango(
                        ejercicio.getFechaInicio(), ejercicio.getFechaFin())) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_CON_MOVIMIENTOS",
                    "El ejercicio tiene movimientos; no se puede eliminar.");
        }

        Ejercicio ultimo = repository.findUltimo()
                .orElseThrow(() -> new EjercicioInvalidoException(
                        "EJERCICIO_NO_EXISTE", "No hay ejercicios para eliminar."));

        if (!Objects.equals(ultimo.getEjercicioId(), ejercicioId)) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_NO_ES_ULTIMO",
                    "Sólo se puede eliminar el último ejercicio.");
        }

        if (!ejercicio.getFechaInicio().isAfter(LocalDate.now())) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_YA_COMENZO",
                    "Sólo se puede eliminar un ejercicio que todavía no comenzó.");
        }

        repository.deleteById(ejercicioId);
    }

}
