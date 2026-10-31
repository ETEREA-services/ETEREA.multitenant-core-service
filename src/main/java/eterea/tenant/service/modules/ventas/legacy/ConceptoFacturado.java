package eterea.tenant.service.modules.ventas.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "conceptosfact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ConceptoFacturado extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave")
    private Long conceptoFacturadoId;

    @Column(name = "clavemovclie")
    private Long clienteMovimientoId;

    @Column(name = "nrolinea")
    private Integer numeroLinea;

    @Column(name = "concepto")
    @Builder.Default
    private String concepto = "";

    @Column(name = "clavedetartic")
    private Long articuloMovimientoId;

}
