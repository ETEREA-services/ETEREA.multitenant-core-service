/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.repository.view;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eterea.tenant.service.modules.platform.legacy.model.view.UsuarioVencimiento;

/**
 * @author daniel
 *
 */
public interface IUsuarioVencimientoRepository extends JpaRepository<UsuarioVencimiento, Long> {

	public List<UsuarioVencimiento> findAllByFechaVencimiento(OffsetDateTime fechaVencimiento);

}
