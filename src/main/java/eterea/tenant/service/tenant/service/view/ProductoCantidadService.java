/**
 * 
 */
package eterea.tenant.service.tenant.service.view;

import java.time.OffsetDateTime;
import java.util.List;

import eterea.tenant.service.tenant.repository.view.IProductoCantidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eterea.tenant.service.tenant.model.view.ProductoCantidad;

/**
 * @author daniel
 *
 */
@Service
public class ProductoCantidadService {
	
	@Autowired
	private IProductoCantidadRepository repository;

	public List<ProductoCantidad> findAllByFechaServicio(OffsetDateTime fechaServicio) {
		return repository.findAllByFechaServicio(fechaServicio);
	}

}
