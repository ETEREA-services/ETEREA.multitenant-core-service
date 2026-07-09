package eterea.tenant.service.tenant.repository;

import eterea.tenant.service.tenant.kotlin.model.CuentaMovimientoApertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaMovimientoAperturaRepository extends JpaRepository<CuentaMovimientoApertura, Long>, CuentaMovimientoAperturaRepositoryCustom {
}
