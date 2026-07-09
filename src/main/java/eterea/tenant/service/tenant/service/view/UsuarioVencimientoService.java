/**
 * 
 */
package eterea.tenant.service.tenant.service.view;

import java.util.List;

import eterea.tenant.service.tenant.repository.view.IUsuarioVencimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eterea.tenant.service.tenant.model.view.UsuarioVencimiento;
import eterea.tenant.service.tool.ToolService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class UsuarioVencimientoService {

	@Autowired
	private IUsuarioVencimientoRepository repository;

	public List<UsuarioVencimiento> findAllToday() {
		log.debug("Today -> {}", ToolService.dateAbsoluteArgentina());
		return repository.findAllByFechaVencimiento(ToolService.dateAbsoluteArgentina());
	}

}
