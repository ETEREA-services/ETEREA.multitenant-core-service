package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class Producto extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prd_id")
    private Integer productoId;

    @Column(name = "prd_nombre")
    @Builder.Default
    private String nombre = "";

    @Column(name = "prd_observaciones")
    @Builder.Default
    private String observaciones = "";

    @Column(name = "prd_deshabilitado")
    @Builder.Default
    private byte deshabilitado = 0;

    @Builder.Default
    private byte traslado = 0;

    @Builder.Default
    private byte puntoEncuentro = 0;

    @Builder.Default
    private byte ventaMostrador = 0;

    @Builder.Default
    private byte ventaInternet = 0;

}
