package eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.ejercicio.domain.model.Ejercicio;
import eterea.tenant.service.modules.contable.ejercicio.domain.ports.out.EjercicioRepository;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.mapper.EjercicioMapper;
import eterea.tenant.service.modules.contable.ejercicio.infrastructure.persistence.repository.JpaEjercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaEjercicioRepositoryAdapter implements EjercicioRepository {

    private final JpaEjercicioRepository jpaRepository;
    private final EjercicioMapper mapper;

    @Override
    public List<Ejercicio> findAll() {
        return jpaRepository.findAllByOrderByEjercicioIdDesc().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Ejercicio> findUltimo() {
        return jpaRepository.findTopByOrderByEjercicioIdDesc()
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Ejercicio> findById(Long ejercicioId) {
        return jpaRepository.findByEjercicioId(ejercicioId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Ejercicio> findByFecha(LocalDate fecha) {
        return jpaRepository
                .findFirstByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(fecha, fecha)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsSolapamiento(LocalDate fechaInicio, LocalDate fechaFin) {
        return jpaRepository.existsByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                fechaFin, fechaInicio);
    }

    @Override
    public boolean existsSolapamientoExcluyendo(
            Long ejercicioId, LocalDate fechaInicio, LocalDate fechaFin) {
        return jpaRepository
                .existsByEjercicioIdNotAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                        ejercicioId, fechaFin, fechaInicio);
    }

    @Override
    public Ejercicio save(Ejercicio ejercicio) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(ejercicio)));
    }

    @Override
    public void deleteById(Long ejercicioId) {
        jpaRepository.deleteById(ejercicioId);
    }

}
