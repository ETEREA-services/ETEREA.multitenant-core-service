package eterea.tenant.service.modules.platform.legacy.client.core;

import eterea.tenant.service.modules.platform.legacy.model.dto.ParametroDto;
import org.springframework.web.bind.annotation.GetMapping;

public interface ParametroClient {

    @GetMapping("/top")
    ParametroDto findTop();

}
