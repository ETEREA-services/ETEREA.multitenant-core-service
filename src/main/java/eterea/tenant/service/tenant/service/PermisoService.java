/**
 * 
 */
package eterea.tenant.service.tenant.service;

import eterea.tenant.service.tenant.exception.PermisoException;
import eterea.tenant.service.tenant.kotlin.model.Permiso;
import eterea.tenant.service.tenant.repository.PermisoRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class PermisoService {

	private final PermisoRepository repository;

	public PermisoService(PermisoRepository repository) {
		this.repository = repository;
	}

	public Permiso findByPermiso(String usuario, String opcion) {
		return repository.findByNombreAndOpcion(usuario, opcion)
				.orElseThrow(() -> new PermisoException(usuario, opcion));
	}

}
