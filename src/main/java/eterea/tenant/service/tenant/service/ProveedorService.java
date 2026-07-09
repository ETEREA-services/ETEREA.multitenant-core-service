/**
 * 
 */
package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.exception.ProveedorException;
import eterea.tenant.service.tenant.kotlin.model.Proveedor;
import eterea.tenant.service.tenant.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ProveedorService {

	private final ProveedorRepository repository;

	public ProveedorService(ProveedorRepository repository) {
		this.repository = repository;
	}

	public Proveedor findByProveedorId(Integer proveedorId) {
		return repository.findByProveedorId(proveedorId).orElseThrow(() -> new ProveedorException(proveedorId));
	}

}
