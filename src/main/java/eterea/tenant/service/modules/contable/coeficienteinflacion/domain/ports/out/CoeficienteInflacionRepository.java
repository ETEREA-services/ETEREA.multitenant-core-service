package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.out;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacion;

import java.util.List;
import java.util.Optional;

public interface CoeficienteInflacionRepository {

    List<CoeficienteInflacion> findAllByAnho(int anho);

    Optional<CoeficienteInflacion> findByAnhoAndMes(int anho, int mes);

    List<Integer> findAnios();

    CoeficienteInflacion save(CoeficienteInflacion coeficiente);

    void deleteById(Long coeficienteInflacionId);

}
