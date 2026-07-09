package eterea.tenant.service.tenant.hexagonal.invoicedata.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClienteResponse {

    private String razonSocial;
    private String domicilio;
    private String cuit;
    private Integer posicionIva;
    private String email;

}
