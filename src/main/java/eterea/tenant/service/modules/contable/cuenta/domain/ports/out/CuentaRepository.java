package eterea.tenant.service.modules.contable.cuenta.domain.ports.out;

import eterea.tenant.service.modules.contable.cuenta.domain.model.Cuenta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CuentaRepository {

    List<Cuenta> findAll();

    List<Cuenta> findAllOrdered();

    List<Cuenta> findAllByNegocioIdOrdered(Integer negocioId);

    List<Cuenta> findAllByCuentaMaestroGreaterThan(BigDecimal zero);

    Optional<Cuenta> findByNumeroCuenta(Long numeroCuenta);

    Optional<Cuenta> findByCuentaMaestro(BigDecimal cuentaMaestro);

    boolean existsByNumeroCuenta(Long numeroCuenta);

    boolean existsByNumeroCuentaGreaterThanAndNumeroCuentaLessThanEqual(
            Long numeroCuentaDesde, Long numeroCuentaHasta);

    Cuenta save(Cuenta cuenta);

    void deleteByNumeroCuenta(Long numeroCuenta);

}
