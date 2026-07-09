package eterea.tenant.service.tenant.client.core;

import eterea.tenant.service.tenant.model.dto.ParametroDto;
import org.springframework.web.bind.annotation.GetMapping;

public interface ParametroClient {

    @GetMapping("/top")
    ParametroDto findTop();

}
