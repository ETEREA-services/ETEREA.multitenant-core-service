package eterea.tenant.service.modules.contable.ejercicio.domain.ports.out;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EjercicioRepository {

    List<Ejercicio> findAll();

    Optional<Ejercicio> findUltimo();

    Optional<Ejercicio> findById(Long ejercicioId);

    Optional<Ejercicio> findByFecha(LocalDate fecha);

    boolean existsSolapamiento(LocalDate fechaInicio, LocalDate fechaFin);

    boolean existsSolapamientoExcluyendo(
            Long ejercicioId, LocalDate fechaInicio, LocalDate fechaFin);

    Ejercicio save(Ejercicio ejercicio);

    void deleteById(Long ejercicioId);

}
