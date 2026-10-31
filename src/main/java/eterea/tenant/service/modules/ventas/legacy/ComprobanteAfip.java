package eterea.tenant.service.modules.ventas.legacy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "compafip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ComprobanteAfip {

    @Id
    @Column(name = "caf_id")
    private Integer comprobanteAfipId;

    @Column(name = "caf_nombre")
    private String nombre;

    @Column(name = "caf_label")
    private String label;

}
