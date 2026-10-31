/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.service;

import eterea.tenant.service.modules.platform.legacy.exception.PermisoException;
import eterea.tenant.service.modules.platform.legacy.Permiso;
import eterea.tenant.service.modules.platform.legacy.repository.PermisoRepository;
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
