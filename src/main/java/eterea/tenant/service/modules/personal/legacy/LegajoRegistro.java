package eterea.tenant.service.modules.personal.legacy;

import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.*;
import java.sql.Time;
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
name = "legajoregistro",
uniqueConstraints = {@UniqueConstraint(columnNames = {"lre_leg_id", "lre_fecha", "lre_hora"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class LegajoRegistro extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lre_id")
    private Long legajoRegistroId;

    @Column(name = "lre_leg_id")
    private Integer legajoId;

    @Column(name = "lre_fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    @Column(name = "lre_hora")
    private Time hora;

    @Column(name = "lre_salida")
    @Builder.Default
    private byte salida = 0;

    @Column(name = "lre_ori_id")
    private Integer origenId;

}
