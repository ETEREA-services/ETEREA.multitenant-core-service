package eterea.tenant.service.modules.platform.legacy;

import eterea.tenant.service.tool.Auditable;
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
@Table(name = "paisafip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class AfipPais extends Auditable {

    @Id
    @Column(name = "pais_id")
    private Integer afipPaisId;

    @Builder.Default
    private String nombre = "";

    @Column(name = "auto_id")
    private Long autoId;

}
