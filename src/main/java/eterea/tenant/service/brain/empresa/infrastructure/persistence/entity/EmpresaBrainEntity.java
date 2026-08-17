package eterea.tenant.service.brain.empresa.infrastructure.persistence.entity;

import eterea.tenant.service.tenant.model.Auditable;
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
@Table(name = "empresa")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaBrainEntity extends Auditable {

    @Id
    private UUID empresaId;

    private String nombre;

}
