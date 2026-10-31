package eterea.tenant.service.modules.reserva.legacy;

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
public class Hotel extends Auditable {

    @Id
    @Column(name = "hot_id")
    private Integer hotelId;

    @Column(name = "hot_nombre")
    @Builder.Default
    private String nombre = "";

    @Builder.Default
    private byte extras = 0;

    @Builder.Default
    private byte paradaTraslado = 0;

    @Builder.Default
    private byte puntoEncuentro = 0;

}
