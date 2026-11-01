package eterea.tenant.service.modules.contable.balance.domain.ports.out;

import eterea.tenant.service.modules.contable.balance.domain.model.BalanceFiltro;
import eterea.tenant.service.modules.contable.balance.domain.model.SaldoCuenta;

import java.time.LocalDate;
import java.util.List;

/**
 * Agregados de movimientos contables para el balance.
 *
 * <p>El puerto devuelve sólo totales por cuenta; el adaptador se encarga de la
 * conversión de moneda y del redondeo por movimiento. No devuelve entidades ni
 * filas de detalle.</p>
 */
public interface BalanceMovimientoPort {

    /**
     * Saldos de {@code movcon} para el rango.
     */
    List<SaldoCuenta> findSaldos(int negocioId, BalanceFiltro filtro);

    /**
     * Saldos de {@code movconapertura} para el rango.
     */
    List<SaldoCuenta> findSaldosApertura(int negocioId, BalanceFiltro filtro);

    /**
     * Fechas del rango que no tienen una cotización disponible para el par
     * elegido. Vacío cuando no se convierte o cuando hay cobertura completa.
     */
    List<LocalDate> findFechasSinCotizacion(int negocioId, BalanceFiltro filtro);

    /**
     * Huella barata para invalidar la caché: última actualización de los
     * movimientos y de las cotizaciones involucradas.
     */
    String watermark(int negocioId, BalanceFiltro filtro);

}
