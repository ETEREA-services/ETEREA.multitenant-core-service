package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CoeficienteInflacionAnioUpdateRequest {

    @NotNull
    @Size(max = 12)
    private List<@Valid @NotNull CoeficienteMensualUpdateRequest> meses;

}
