package eterea.tenant.service.modules.ventas.legacy;

import eterea.tenant.service.modules.ventas.legacy.ComprobanteFaltante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComprobanteFaltanteRepository extends JpaRepository<ComprobanteFaltante, Long>, ComprobanteFaltanteRepositoryCustom {

}
