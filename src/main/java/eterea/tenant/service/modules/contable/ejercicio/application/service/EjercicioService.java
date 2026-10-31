package eterea.tenant.service.modules.contable.ejercicio.application.service;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.model.EjercicioSugerido;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.CambiarBloqueoEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.CreateEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.DeleteEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.FindAllEjerciciosUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.FindEjercicioByIdUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.SugerirSiguienteEjercicioUseCase;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.in.UpdateEjercicioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EjercicioService {

    private final FindAllEjerciciosUseCase findAllEjerciciosUseCase;
    private final FindEjercicioByIdUseCase findEjercicioByIdUseCase;
    private final SugerirSiguienteEjercicioUseCase sugerirSiguienteEjercicioUseCase;
    private final CreateEjercicioUseCase createEjercicioUseCase;
    private final UpdateEjercicioUseCase updateEjercicioUseCase;
    private final CambiarBloqueoEjercicioUseCase cambiarBloqueoEjercicioUseCase;
    private final DeleteEjercicioUseCase deleteEjercicioUseCase;

    public List<Ejercicio> findAll() {
        return findAllEjerciciosUseCase.findAll();
    }

    public Ejercicio findById(Long ejercicioId) {
        return findEjercicioByIdUseCase.findById(ejercicioId);
    }

    public EjercicioSugerido sugerirSiguiente() {
        return sugerirSiguienteEjercicioUseCase.sugerir();
    }

    public Ejercicio create(Ejercicio nuevo) {
        return createEjercicioUseCase.create(nuevo);
    }

    public Ejercicio update(Long ejercicioId, Ejercicio cambios) {
        return updateEjercicioUseCase.update(ejercicioId, cambios);
    }

    public Ejercicio cambiarBloqueo(Long ejercicioId, boolean bloqueado) {
        return cambiarBloqueoEjercicioUseCase.cambiarBloqueo(ejercicioId, bloqueado);
    }

    public void delete(Long ejercicioId) {
        deleteEjercicioUseCase.delete(ejercicioId);
    }

}
