package eterea.tenant.service.modules.platform.legacy;

import eterea.tenant.service.tool.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clienteinternet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ClienteInternet extends Auditable {

    @Id
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "password")
    @Size(max = 64)
    private String password;

}
