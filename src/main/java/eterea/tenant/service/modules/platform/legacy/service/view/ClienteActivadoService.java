/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.service.view;

import java.util.List;

import eterea.tenant.service.modules.platform.legacy.ClienteActivado;
import eterea.tenant.service.modules.platform.legacy.repository.view.IClienteActivadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ClienteActivadoService {
	@Autowired
	private IClienteActivadoRepository repository;

	public List<ClienteActivado> findAll() {
		return repository.findAll(Sort.by(Sort.Order.asc("nombrefantasia"), Sort.Order.asc("razonsocial")));
	}

}
