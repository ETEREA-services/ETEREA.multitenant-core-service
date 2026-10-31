/**
 * 
 */
package eterea.tenant.service.modules.compras.legacy.service;

import eterea.tenant.service.modules.compras.legacy.exception.ProveedorException;
import eterea.tenant.service.modules.compras.legacy.Proveedor;
import eterea.tenant.service.modules.compras.legacy.repository.ProveedorRepository;
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
