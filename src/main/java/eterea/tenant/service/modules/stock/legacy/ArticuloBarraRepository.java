package eterea.tenant.service.modules.stock.legacy;

import eterea.tenant.service.modules.stock.legacy.ArticuloBarra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import java.util.Optional;
import java.util.List;

public interface ArticuloBarraRepository extends JpaRepository<ArticuloBarra, String> {

    List<ArticuloBarra> findAllByArticuloId(String articuloId);

    Optional<ArticuloBarra> findByCodigoBarras(String codigoBarras);

    @Modifying
    void deleteByCodigoBarras(String codigoBarras);

}
