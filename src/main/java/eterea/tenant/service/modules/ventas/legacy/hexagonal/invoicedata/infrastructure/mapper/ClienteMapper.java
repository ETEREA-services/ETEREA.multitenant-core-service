package eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.mapper;

import eterea.tenant.service.modules.ventas.legacy.hexagonal.invoicedata.infrastructure.dto.ClienteResponse;
import eterea.tenant.service.modules.platform.legacy.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteResponse toResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return ClienteResponse.builder()
                .razonSocial(cliente.getRazonSocial())
                .domicilio(cliente.getDomicilio())
                .cuit(cliente.getCuit())
                .posicionIva(cliente.getPosicionIva())
                .email(cliente.getEmail())
                .build();
    }

}
