package eterea.tenant.service.modules.stock.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
name = "articulohabil",
uniqueConstraints = {@UniqueConstraint(columnNames = {"aha_fecha", "aha_serie", "aha_inicial"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ArticuloHabilitado extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aha_id")
    private Long articuloHabilitadoId;

    @Column(name = "aha_fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaHabilitacion;

    @Column(name = "aha_serie")
    @Builder.Default
    private String serie = "";

    @Column(name = "aha_inicial")
    @Builder.Default
    private long numeroInicial = 0;

    @Column(name = "aha_final")
    @Builder.Default
    private long numeroFinal = 0;

    @Column(name = "aha_art_id")
    private String articuloId;

}
