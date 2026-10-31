package eterea.tenant.service.modules.contable.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Immutable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "vw_asientos")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class AsientoView {

    @Id
    private String uniqueId;

    private Integer negocioId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    @Column(name = "nrocomp")
    @Builder.Default
    private int orden = 0;

    @Column(name = "cgotcomp")
    private Integer comprobanteId;

    @Builder.Default
    private String concepto = "";

    @Column(name = "cic_id")
    private Long cierreCajaId;

    @Builder.Default
    private BigDecimal debe = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal haber = BigDecimal.ZERO;

}
