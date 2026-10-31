package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoeficienteMensualUpdateRequest {

    @NotNull
    @Min(1)
    @Max(12)
    private Integer mes;

    /** {@code null} indica que el mes debe limpiarse. */
    @DecimalMin("0")
    private BigDecimal coeficiente;

}
