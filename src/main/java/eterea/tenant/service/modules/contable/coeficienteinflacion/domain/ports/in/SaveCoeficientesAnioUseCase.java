package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.ports.in;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteInflacionAnio;
import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model.CoeficienteMensualCambio;

import java.util.List;

public interface SaveCoeficientesAnioUseCase {

    CoeficienteInflacionAnio save(int anho, List<CoeficienteMensualCambio> cambios);

}
