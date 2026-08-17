package eterea.tenant.service.brain.negocio.infrastructure.persistence.entity;

import eterea.tenant.service.tenant.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "negocio")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioBrainEntity extends Auditable {

    @Id
    private Integer negocioId;

    private UUID empresaId;
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
