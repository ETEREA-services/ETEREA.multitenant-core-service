package eterea.tenant.service.modules.contable.cuenta.domain.ports.out;

public interface CuentaMovimientoPort {

    boolean existeMovimientoEnCuenta(Long numeroCuenta);

    boolean existeMovimientoEnRango(Long numeroCuentaDesde, Long numeroCuentaHasta);
}
