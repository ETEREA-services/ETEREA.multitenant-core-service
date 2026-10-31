package eterea.tenant.service.modules.contable.legacy;

import eterea.tenant.service.modules.contable.legacy.CuentaMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;
import java.util.*;

@Repository
public interface CuentaMovimientoRepository extends JpaRepository<CuentaMovimiento, Long>, CuentaMovimientoRepositoryCustom {

    List<CuentaMovimiento> findAllByFechaAndOrden(OffsetDateTime fechaContable, int ordenContable);

    List<CuentaMovimiento> findAllByFechaBetween(OffsetDateTime fechaDesde, OffsetDateTime fechaHasta);

    Optional<CuentaMovimiento> findByCuentaMovimientoId(Long cuentaMovimientoId);

    Optional<CuentaMovimiento> findFirstByFechaAndOrdenOrderByItemDesc(OffsetDateTime fecha, Integer orden);

    Optional<CuentaMovimiento> findFirstByFechaOrderByOrdenDesc(OffsetDateTime fecha);

    boolean existsByNumeroCuenta(long numeroCuenta);

    boolean existsByNumeroCuentaBetween(long numeroCuentaDesde, long numeroCuentaHasta);

    boolean existsByEjercicioId(int ejercicioId);

    boolean existsByFechaGreaterThanEqualAndFechaLessThan(OffsetDateTime fechaDesde, OffsetDateTime fechaHasta);

}
