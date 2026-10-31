package eterea.tenant.service.modules.ventas.legacy;

import eterea.tenant.service.modules.ventas.legacy.RegistroCae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RegistroCaeRepository extends JpaRepository<RegistroCae, Long> {

    Optional<RegistroCae> findByComprobanteIdAndPuntoVentaAndNumeroComprobante(int comprobanteId, int puntoVenta, long numeroComprobante);

}
