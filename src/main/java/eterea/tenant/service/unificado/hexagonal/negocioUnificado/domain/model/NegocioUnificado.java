package eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model;

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
public class NegocioUnificado {

    private Integer negocioId;
    private String nombre;
    private String databaseIp;
    private String databasePort;
    private String databaseName;
    private String databaseUsername;
    private Byte backup;
    private String tenantId;

}
