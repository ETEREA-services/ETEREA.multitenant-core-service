package eterea.tenant.service.brain.negocio.infrastructure.web.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Resumen seguro de un negocio, sin datos de conexión. */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioBrainResumenResponse {

    private Integer negocioId;
    private UUID empresaId;
    private String nombre;
    private String tenantId;
}
