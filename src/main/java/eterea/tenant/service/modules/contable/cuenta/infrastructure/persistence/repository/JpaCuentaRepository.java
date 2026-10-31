package eterea.tenant.service.modules.contable.cuenta.infrastructure.persistence.repository;

import eterea.tenant.service.modules.contable.cuenta.infrastructure.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface JpaCuentaRepository extends JpaRepository<CuentaEntity, Long> {

    List<CuentaEntity> findAllByOrderByNumeroCuentaAsc();

    List<CuentaEntity> findAllByNegocioIdOrderByNumeroCuentaAsc(Integer negocioId);

    List<CuentaEntity> findAllByCuentaMaestroGreaterThan(BigDecimal zero);

    Optional<CuentaEntity> findByNumeroCuenta(Long numeroCuenta);

    Optional<CuentaEntity> findByCuentaMaestro(BigDecimal cuentaMaestro);

    boolean existsByNumeroCuentaGreaterThanAndNumeroCuentaLessThanEqual(
            Long numeroCuentaDesde, Long numeroCuentaHasta);

}
