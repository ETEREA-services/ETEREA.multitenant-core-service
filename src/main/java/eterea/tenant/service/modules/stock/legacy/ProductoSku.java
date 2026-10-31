package eterea.tenant.service.modules.stock.legacy;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ProductoSku extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoSkuId;

    @Builder.Default
    private String sku = "";

    @Builder.Default
    private byte lunes = 0;

    @Builder.Default
    private byte martes = 0;

    @Builder.Default
    private byte miercoles = 0;

    @Builder.Default
    private byte jueves = 0;

    @Builder.Default
    private byte viernes = 0;

    @Builder.Default
    private byte sabado = 0;

    @Builder.Default
    private byte domingo = 0;

    @Builder.Default
    private byte feriado = 0;

    private Integer productoIdPaxMayor;

    private Integer productoIdPaxMenor;

    private Integer productoIdPaxInfante;

    @OneToOne(optional = true)
    @JoinColumn(name = "productoIdPaxMayor", insertable = false, updatable = false)
    private Producto productoPaxMayor;

    @OneToOne(optional = true)
    @JoinColumn(name = "productoIdPaxMenor", insertable = false, updatable = false)
    private Producto productoPaxMenor;

    @OneToOne(optional = true)
    @JoinColumn(name = "productoIdPaxInfante", insertable = false, updatable = false)
    private Producto productoPaxInfante;

}
