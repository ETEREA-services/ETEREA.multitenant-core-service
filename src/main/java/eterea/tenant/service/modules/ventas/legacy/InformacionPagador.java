package eterea.tenant.service.modules.ventas.legacy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class InformacionPagador {

    private Long orderNumberId;

    @Builder.Default
    private String eMail = "";

    @Builder.Default
    private String nombre = "";

    @Builder.Default
    private String numeroDocumento = "";

    private String telefono;

    @Builder.Default
    private String tipoDocumento = "";

}
