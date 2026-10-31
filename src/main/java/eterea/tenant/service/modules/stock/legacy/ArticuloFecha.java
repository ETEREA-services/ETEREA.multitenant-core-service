package eterea.tenant.service.modules.stock.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.*;
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
@Table(name = "articulofecha", uniqueConstraints = {@UniqueConstraint(columnNames = {"articulo_id", "fecha"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ArticuloFecha extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articulofecha_id")
    private Long articuloFechaId;

    @Builder.Default
    private String articuloId = "";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    @Builder.Default
    private BigDecimal precioUsd = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal precioArs = BigDecimal.ZERO;

}
