package eterea.tenant.service.tenant.hexagonal.transferencia.domain.model;

import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transferencia implements Jsonifyable {

    private Long transferenciaId;
    private Integer negocioIdDesde;
    private Integer negocioIdHasta;
    private Long numeroTransferencia;
    private OffsetDateTime fecha;
    private BigDecimal total;
    private Integer ordenContable;
    private Byte transferido;
    private Integer comprobanteId;
    private OffsetDateTime fechaOtro;

}
