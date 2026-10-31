package eterea.tenant.service.modules.contable.coeficienteinflacion.application.service;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteMensualCambio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.in.FindAniosCoeficientesUseCase;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.in.FindCoeficientesAnioUseCase;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.in.SaveCoeficientesAnioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoeficienteInflacionService {

    private final FindCoeficientesAnioUseCase findCoeficientesAnioUseCase;
    private final FindAniosCoeficientesUseCase findAniosCoeficientesUseCase;
    private final SaveCoeficientesAnioUseCase saveCoeficientesAnioUseCase;

    public CoeficienteInflacionAnio findAnio(int anho) {
        return findCoeficientesAnioUseCase.find(anho);
    }

    public List<Integer> findAnios() {
        return findAniosCoeficientesUseCase.findAll();
    }

    public CoeficienteInflacionAnio save(int anho, List<CoeficienteMensualCambio> cambios) {
        return saveCoeficientesAnioUseCase.save(anho, cambios);
    }

}
