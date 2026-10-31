/**
 * 
 */
package eterea.tenant.service.modules.compras.legacy.repository;

import java.util.Optional;

import eterea.tenant.service.modules.compras.legacy.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

	Optional<Proveedor> findByProveedorId(Integer proveedorId);

}
