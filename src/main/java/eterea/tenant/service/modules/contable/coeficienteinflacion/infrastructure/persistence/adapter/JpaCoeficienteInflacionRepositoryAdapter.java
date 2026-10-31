package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.adapter;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.exception.CoeficienteInflacionInvalidaException;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacion;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.out.CoeficienteInflacionRepository;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.mapper.CoeficienteInflacionMapper;
import eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.persistence.repository.JpaCoeficienteInflacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaCoeficienteInflacionRepositoryAdapter
        implements CoeficienteInflacionRepository {

    private final JpaCoeficienteInflacionRepository jpaRepository;
    private final CoeficienteInflacionMapper mapper;

    @Override
    public List<CoeficienteInflacion> findAllByAnho(int anho) {
        return jpaRepository.findAllByAnhoOrderByMesAsc(anho).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CoeficienteInflacion> findByAnhoAndMes(int anho, int mes) {
        return jpaRepository.findFirstByAnhoAndMesOrderByCoeficienteInflacionIdDesc(anho, mes)
                .map(mapper::toDomain);
    }

    @Override
    public List<Integer> findAnios() {
        return jpaRepository.findAniosDistintos();
    }

    @Override
    public CoeficienteInflacion save(CoeficienteInflacion coeficiente) {
        try {
            return mapper.toDomain(jpaRepository.saveAndFlush(mapper.toEntity(coeficiente)));
        } catch (DataIntegrityViolationException exception) {
            throw new CoeficienteInflacionInvalidaException(
                    "COEFICIENTE_CONFLICTO",
                    "Ya existe un coeficiente para ese año y mes. Recargá la pantalla.");
        }
    }

    @Override
    public void deleteById(Long coeficienteInflacionId) {
        if (coeficienteInflacionId != null) {
            jpaRepository.deleteById(coeficienteInflacionId);
        }
    }

}
