package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.RubroListaPrecio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface RubroListaPrecioRepository extends JpaRepository<RubroListaPrecio, UUID> {

    List<RubroListaPrecio> findAllByOrderByRubroDescripcion();

    List<RubroListaPrecio> findAllByPublicarOrderByRubroDescripcion(byte publicar);

    Optional<RubroListaPrecio> findByRubroId(Long rubroId);

}
