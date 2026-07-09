package eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.persistence.entity;

import eterea.tenant.service.tenant.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "negocio")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioUnificadoEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "neg_id")
    private Integer negocioId;

    @Column(name = "neg_nombre")
    private String nombre;

    @Column(name = "neg_ip")
    private String databaseIp;

    private String databasePort;

    @Column(name = "neg_db")
    private String databaseName;

    @Column(name = "neg_user")
    private String databaseUsername;

    @Builder.Default
    private Byte backup = 0;

    private String tenantId;

}
