package eterea.tenant.service.unificado.hexagonal.usuarioNegocioUnificado.infrastructure.web.dto;

import eterea.tenant.service.unificado.hexagonal.negocioUnificado.infrastructure.web.dto.NegocioUnificadoResponse;
import eterea.tenant.service.unificado.hexagonal.usuarioUnificado.infrastructure.web.dto.UsuarioUnificadoResponse;
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
public class UsuarioNegocioUnificadoResponse {

    private Integer usuarioNegocioId;
    private Long usuarioId;
    private Integer negocioId;
    private UsuarioUnificadoResponse usuario;
    private NegocioUnificadoResponse negocio;

}
