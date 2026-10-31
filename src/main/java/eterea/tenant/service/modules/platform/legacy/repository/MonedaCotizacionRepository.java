package eterea.tenant.service.modules.platform.legacy.repository;

import eterea.tenant.service.modules.platform.legacy.MonedaCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonedaCotizacionRepository extends JpaRepository<MonedaCotizacion, UUID> {

    List<MonedaCotizacion> findAllByMonedaIdOrigenAndMonedaIdDestinoAndFechaBetween(Integer monedaIdOrigen, Integer monedaIdDestino, OffsetDateTime fechaDesde, OffsetDateTime fechaHasta);

}
