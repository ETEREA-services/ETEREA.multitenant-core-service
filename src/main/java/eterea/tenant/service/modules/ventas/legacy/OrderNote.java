package eterea.tenant.service.modules.ventas.legacy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import eterea.tenant.service.tool.Jsonifyable;
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
public class OrderNote implements Jsonifyable {

    private Long orderNumberId;

    @Builder.Default
    private String orderStatus = "";

    private OffsetDateTime orderDate;

    private OffsetDateTime paidDate;

    private OffsetDateTime completedDate;

    private OffsetDateTime modifiedDate;

    @Builder.Default
    private String orderCurrency = "";

    @Builder.Default
    private String customerNote = "";

    @Builder.Default
    private String billingFirstName = "";

    @Builder.Default
    private String billingLastName = "";

    @Builder.Default
    private String billingFullName = "";

    @Builder.Default
    private String billingDniPasaporte = "";

    @Builder.Default
    private String billingAddress = "";

    @Builder.Default
    private String billingCity = "";

    @Builder.Default
    private String billingState = "";

    @Builder.Default
    private String billingPostCode = "";

    @Builder.Default
    private String billingCountry = "";

    @Builder.Default
    private String billingEmail = "";

    @Builder.Default
    private String billingPhone = "";

    @Builder.Default
    private String shippingFirstName = "";

    @Builder.Default
    private String shippingLastName = "";

    @Builder.Default
    private String shippingFullName = "";

    @Builder.Default
    private String shippingAddress = "";

    @Builder.Default
    private String shippingCity = "";

    @Builder.Default
    private String shippingState = "";

    @Builder.Default
    private String shippingPostCode = "";

    @Builder.Default
    private String shippingCountryFull = "";

    @Builder.Default
    private String paymentMethodTitle = "";

    @Builder.Default
    private String cartDiscount = "";

    @Builder.Default
    private BigDecimal orderSubtotal = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal orderSubtotalRefunded = BigDecimal.ZERO;

    @Builder.Default
    private String shippingMethodTitle = "";

    @Builder.Default
    private String orderShipping = "";

    @Builder.Default
    private BigDecimal orderShippingRefunded = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal orderTotal = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal orderTotalTax = BigDecimal.ZERO;

    @Builder.Default
    private String orderNotes = "";

    private List<Product> products;

    private Payment payment;

}
