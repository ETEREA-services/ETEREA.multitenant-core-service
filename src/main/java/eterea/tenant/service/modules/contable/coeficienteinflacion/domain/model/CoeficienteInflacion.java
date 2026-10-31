package eterea.tenant.service.modules.contable.coeficienteinflacion.domain.model;

import eterea.tenant.service.tool.Jsonifyable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoeficienteInflacion implements Jsonifyable {

    private Long coeficienteInflacionId;
    private Integer anho;
    private Integer mes;
    private BigDecimal coeficiente;
    private LocalDateTime created;

}
