package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.domain.model;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.domain.model.NegocioUnificado;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.domain.model.UsuarioUnificado;
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
public class UsuarioNegocioUnificado {

    private Integer usuarioNegocioId;
    private Long usuarioId;
    private Integer negocioId;
    private NegocioUnificado negocio;
    private UsuarioUnificado usuario;

}
