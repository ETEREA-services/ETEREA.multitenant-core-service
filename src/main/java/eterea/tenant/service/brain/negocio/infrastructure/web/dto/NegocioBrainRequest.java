package eterea.tenant.service.brain.negocio.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioBrainRequest {

    @NotNull
    private Integer negocioId;

    @NotNull
    private UUID empresaId;

    @NotBlank
    private String nombre;
    private String databaseName;
    private String databaseIp;
    private String databasePort;
    private String databaseUsername;
    private String databasePassword;
    private String backendIp;
    private String backendPort;
    private String tenantId;
}
