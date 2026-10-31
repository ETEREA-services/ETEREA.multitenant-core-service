package eterea.tenant.service.modules.ventas.legacy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
public class Product {

    private Long productId;

    private Long orderNumberId;

    @Builder.Default
    private String sku = "";

    @Builder.Default
    private int lineId = 0;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private int qty = 0;

    @Builder.Default
    private BigDecimal itemPrice = BigDecimal.ZERO;

    private OffsetDateTime bookingStart;

    private OffsetDateTime bookingEnd;

    @Builder.Default
    private int bookingDuration = 0;

    @Builder.Default
    private int bookingPersons = 0;

    @Builder.Default
    private String personTypes = "";

    @Builder.Default
    private String serviciosAdicionales = "";

    @Builder.Default
    private String puntoDeEncuentro = "";

    @Builder.Default
    private String encuentroHotel = "";

}
