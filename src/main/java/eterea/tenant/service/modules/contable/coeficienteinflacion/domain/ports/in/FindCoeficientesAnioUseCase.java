package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.in;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;

public interface FindCoeficientesAnioUseCase {

    CoeficienteInflacionAnio find(int anho);

}
