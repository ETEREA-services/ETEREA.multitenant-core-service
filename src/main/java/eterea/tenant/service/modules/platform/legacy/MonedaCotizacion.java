package eterea.tenant.service.modules.platform.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"fecha", "monedaIdOrigen", "monedaIdDestino"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class MonedaCotizacion extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID monedaCotizacionId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    private Integer monedaIdOrigen;

    private Double cotizacionOrigen;

    @Builder.Default
    private byte copiaOrigen = 0;

    private Integer monedaIdDestino;

    private Double cotizacionDestino;

    @Builder.Default
    private byte copiaDestino = 0;

    private Double coeficiente;

}
