package eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.repository;

import eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.entity.EjercicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaEjercicioRepository extends JpaRepository<EjercicioEntity, Long> {

    List<EjercicioEntity> findAllByOrderByEjercicioIdDesc();

    Optional<EjercicioEntity> findTopByOrderByEjercicioIdDesc();

    Optional<EjercicioEntity> findByEjercicioId(Long ejercicioId);

    Optional<EjercicioEntity>
    findFirstByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            LocalDate fechaFin, LocalDate fechaInicio);

    boolean existsByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            LocalDate fechaFin, LocalDate fechaInicio);

    boolean existsByEjercicioIdNotAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long ejercicioId, LocalDate fechaFin, LocalDate fechaInicio);

}
