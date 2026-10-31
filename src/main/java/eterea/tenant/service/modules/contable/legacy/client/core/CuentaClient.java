package eterea.tenant.service.modules.contable.legacy.client.core;

import eterea.tenant.service.modules.contable.legacy.CuentaDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CuentaClient {

    @GetMapping("/{numeroCuenta}")
    CuentaDto findByNumeroCuenta(@PathVariable Long numeroCuenta);

}
