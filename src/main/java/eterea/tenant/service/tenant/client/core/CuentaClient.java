package eterea.tenant.service.tenant.client.core;

import eterea.tenant.service.tenant.model.dto.CuentaDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CuentaClient {

    @GetMapping("/{numeroCuenta}")
    CuentaDto findByNumeroCuenta(@PathVariable Long numeroCuenta);

}
