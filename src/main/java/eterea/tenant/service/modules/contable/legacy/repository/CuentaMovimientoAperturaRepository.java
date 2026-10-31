package eterea.tenant.service.modules.contable.legacy.repository;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimientoApertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaMovimientoAperturaRepository extends JpaRepository<CuentaMovimientoApertura, Long>, CuentaMovimientoAperturaRepositoryCustom {
}
