package eterea.tenant.service.modules.ventas.legacy;

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
public class ProductTransaction {

    private Long productTransactionId;

    private Long orderNumberId;

    @Builder.Default
    private String nombreProducto = "";

    private String montoProducto;

}
