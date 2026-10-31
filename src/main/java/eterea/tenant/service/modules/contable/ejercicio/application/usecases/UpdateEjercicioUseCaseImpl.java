package eterea.tenant.service.modules.contable.ejercicio.application.usecases;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioValidator;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.UpdateEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioMovimientoPort;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
public class UpdateEjercicioUseCaseImpl implements UpdateEjercicioUseCase {

    private final EjercicioRepository repository;
    private final EjercicioMovimientoPort movimientoPort;

    public UpdateEjercicioUseCaseImpl(
            EjercicioRepository repository, EjercicioMovimientoPort movimientoPort) {
        this.repository = repository;
        this.movimientoPort = movimientoPort;
    }

    @Override
    @Transactional
    public Ejercicio update(Long ejercicioId, Ejercicio cambios) {
        EjercicioValidator.validarId(ejercicioId);
        Ejercicio existente = repository.findById(ejercicioId)
                .orElseThrow(() -> new EjercicioInvalidoException(
                        "EJERCICIO_NO_EXISTE",
                        "El ejercicio " + ejercicioId + " no existe."));

        String nombre = EjercicioValidator.normalizarNombre(cambios.getNombre());
        EjercicioValidator.validarFechas(cambios.getFechaInicio(), cambios.getFechaFin());

        boolean fechasCambiaron = !Objects.equals(
                        existente.getFechaInicio(), cambios.getFechaInicio())
                || !Objects.equals(existente.getFechaFin(), cambios.getFechaFin());

        if (fechasCambiaron
                && (movimientoPort.existeMovimientoEnEjercicio(ejercicioId)
                || movimientoPort.existeMovimientoEnRango(
                        existente.getFechaInicio(), existente.getFechaFin()))) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_CON_MOVIMIENTOS",
                    "El ejercicio tiene movimientos; no se pueden cambiar sus fechas.");
        }

        if (repository.existsSolapamientoExcluyendo(
                ejercicioId, cambios.getFechaInicio(), cambios.getFechaFin())) {
            throw new EjercicioInvalidoException(
                    "EJERCICIO_SOLAPADO",
                    "El ejercicio se superpone con otro ejercicio existente.");
        }

        existente.setNombre(nombre);
        existente.setFechaInicio(cambios.getFechaInicio());
        existente.setFechaFin(cambios.getFechaFin());

        return repository.save(existente);
    }

}
