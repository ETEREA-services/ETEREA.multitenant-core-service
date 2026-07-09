/**
 * 
 */
package eterea.tenant.service.tenant.controller.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eterea.tenant.service.tenant.service.facade.VencimientoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/tenant/core/vencimiento")
@RequiredArgsConstructor
public class VencimientoController {

	private final VencimientoService service;

	@GetMapping("/notificaciondia")
	@Scheduled(cron = "0 0 9 * * *")
	private String notificaciondia() {
		return service.notificaciondia();
	}

}
