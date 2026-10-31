package eterea.tenant.service.modules.stock.legacy.repository;

import eterea.tenant.service.modules.stock.legacy.GrupoProducto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoProductoRepository extends JpaRepository<GrupoProducto, Long> {

    Optional<GrupoProducto> findByGrupoIdAndProductoId(Integer grupoId, Integer productoId);

    List<GrupoProducto> findByProductoId(Integer productoId);

}
