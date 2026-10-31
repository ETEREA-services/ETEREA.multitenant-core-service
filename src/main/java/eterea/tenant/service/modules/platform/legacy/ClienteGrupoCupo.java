package eterea.tenant.service.modules.platform.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
name = "clientegrupocupo",
uniqueConstraints = {@UniqueConstraint(columnNames = {"cliente_id", "grupo_id", "dias"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ClienteGrupoCupo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientegrupocupo_id")
    private Long clientegrupocupoId;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "grupo_id")
    private Integer grupoId;

    @NotNull
    @Column(name = "dias")
    @Builder.Default
    private int dias = 0;

    @NotNull
    @Column(name = "cantidad")
    @Builder.Default
    private int cantidad = 0;

}
