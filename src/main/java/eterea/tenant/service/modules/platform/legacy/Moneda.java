package eterea.tenant.service.modules.platform.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Moneda extends Auditable {

    @Id
    @Column(name = "mon_id")
    private Integer monedaId;

    @Column(name = "mon_nombre")
    @Builder.Default
    private String nombre = "";

    @Column(name = "mon_simbolo")
    @Builder.Default
    private String simbolo = "";

    @Column(name = "mon_cva_id")
    private Integer valorId;

}
