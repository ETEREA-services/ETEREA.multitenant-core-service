package eterea.tenant.service.modules.ventas.legacy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Payment {

    private Long orderNumberId;

    @Builder.Default
    private String transaccionComercioId = "";

    @Builder.Default
    private String transaccionPlataformaId = "";

    @Builder.Default
    private String tipo = "";

    @Builder.Default
    private BigDecimal monto = BigDecimal.ZERO;

    @Builder.Default
    private String estado = "";

    @Builder.Default
    private String detalle = "";

    private String metodoPago;

    private String medioPago;

    private Integer estadoId;

    private Integer cuotas;

    private String informacionAdicional;

    private String marcaTarjeta;

    private String informacionAdicionalLink;

    private OffsetDateTime fechaTransaccion;

    private OffsetDateTime fechaPago;

    private InformacionPagador informacionPagador;

    private List<ProductTransaction> productTransactions;

}
