package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoeficienteInflacionAnioResponse {

    private int anho;
    private List<CoeficienteMensualResponse> meses;
    private int cargados;
    private int total;

}
