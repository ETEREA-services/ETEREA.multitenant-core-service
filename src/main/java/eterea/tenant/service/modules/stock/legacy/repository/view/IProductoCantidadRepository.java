/**
 * 
 */
package eterea.tenant.service.modules.stock.legacy.repository.view;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eterea.tenant.service.modules.stock.legacy.model.view.ProductoCantidad;
import eterea.tenant.service.modules.stock.legacy.model.view.pk.ProductoCantidadPk;

/**
 * @author daniel
 *
 */
@Repository
public interface IProductoCantidadRepository extends JpaRepository<ProductoCantidad, ProductoCantidadPk> {

	public List<ProductoCantidad> findAllByFechaServicio(OffsetDateTime fechaServicio);

}
