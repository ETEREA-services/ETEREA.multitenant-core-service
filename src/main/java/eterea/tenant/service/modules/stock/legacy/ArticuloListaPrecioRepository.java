package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.ArticuloListaPrecio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ArticuloListaPrecioRepository extends JpaRepository<ArticuloListaPrecio, UUID> {

    Page<ArticuloListaPrecio> findAllByPublicarAndArticuloPrecioVentaConIvaGreaterThan(byte publicar, BigDecimal precioVentaConIva, Pageable pageable);

    Optional<ArticuloListaPrecio> findByArticuloId(String articuloId);

    Page<ArticuloListaPrecio> findAllByPublicarAndArticuloPrecioVentaConIvaGreaterThanAndArticuloRubroId(byte publicar, BigDecimal precioVentaConIva, long rubroId, Pageable pageable);

}
