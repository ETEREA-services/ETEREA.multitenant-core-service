/**
 * 
 */
package eterea.tenant.service.tenant.repository.view;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eterea.tenant.service.tenant.model.view.ProductoCantidad;
import eterea.tenant.service.tenant.model.view.pk.ProductoCantidadPk;

/**
 * @author daniel
 *
 */
@Repository
public interface IProductoCantidadRepository extends JpaRepository<ProductoCantidad, ProductoCantidadPk> {

	public List<ProductoCantidad> findAllByFechaServicio(OffsetDateTime fechaServicio);

}
