/**
 * 
 */
package eterea.tenant.service.modules.stock.legacy;

import java.util.List;

import eterea.tenant.service.modules.stock.legacy.exception.ProductoArticuloException;
import eterea.tenant.service.modules.stock.legacy.ProductoArticulo;
import eterea.tenant.service.modules.stock.legacy.ProductoArticuloRepository;
import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ProductoArticuloService {

	private final ProductoArticuloRepository repository;

	public ProductoArticuloService(ProductoArticuloRepository repository) {
		this.repository = repository;
	}

	public List<ProductoArticulo> findAllByProductoId(Integer productoId) {
		return repository.findAllByProductoId(productoId);
	}

	public ProductoArticulo findByProductoIdAndArticuloId(Integer productoId, String articuloId) {
		return repository.findByProductoIdAndArticuloId(productoId, articuloId).orElseThrow(() -> new ProductoArticuloException(productoId, articuloId));
	}

	public ProductoArticulo add(ProductoArticulo productoarticulo) {
		repository.save(productoarticulo);
		return productoarticulo;
	}

	public void delete(Long productoarticuloId) {
		repository.deleteById(productoarticuloId);
	}

}
