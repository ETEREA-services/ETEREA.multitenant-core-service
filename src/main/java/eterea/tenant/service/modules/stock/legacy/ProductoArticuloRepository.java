package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.ProductoArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProductoArticuloRepository extends JpaRepository<ProductoArticulo, Long> {

    List<ProductoArticulo> findAllByProductoId(Integer productoId);

    Optional<ProductoArticulo> findByProductoIdAndArticuloId(Integer productoId, String articuloId);

}
