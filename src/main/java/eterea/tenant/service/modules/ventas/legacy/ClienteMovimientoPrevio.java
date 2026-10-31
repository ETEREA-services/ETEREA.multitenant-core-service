package eterea.tenant.service.modules.ventas.legacy;

import eterea.tenant.service.modules.platform.legacy.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import eterea.tenant.service.tool.Auditable;
import eterea.tenant.service.tool.Jsonifier;
import eterea.tenant.service.tool.Jsonifyable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clientemovimientoprevio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ClienteMovimientoPrevio extends Auditable implements Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientemovimientoprevio_id")
    private Long clienteMovimientoPrevioId;

    @Builder.Default
    private byte enConstruccion = 0;

    @Builder.Default
    private byte tieneArticulos = 0;

    @Builder.Default
    private byte ticketImpreso = 0;

    private Long clienteId;

    private Integer puntoVenta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    @Builder.Default
    private BigDecimal importe = BigDecimal.ZERO;

    @Column(name = "clientemovimiento_id")
    private Long clienteMovimientoId;

    @OneToOne(optional = true)
    @JoinColumn(name = "clienteId", insertable = false, updatable = false)
    private Cliente cliente;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientemovimientoprevio_id", insertable = false, updatable = false)
    private List<ArticuloMovimientoPrevio> articuloMovimientoPrevios;

}
