package eterea.tenant.service.modules.contable.mayor.domain.ports.out;

import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDetalle;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFila;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorDiscriminadoFiltro;
import eterea.tenant.service.modules.contable.mayor.domain.model.MayorFiltro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Lectura de movimientos de {@code movcon} para el Mayor.
 *
 * <p>El adaptador aplica el filtro de negocio y resuelve la conversión de
 * moneda; el dominio sólo recibe importes ya convertidos y redondeados a 5
 * decimales cuando corresponde.</p>
 */
public interface MayorMovimientoPort {

    List<MayorDetalle> findDetalle(int negocioId, MayorFiltro filtro);

    /**
     * Saldo anterior a {@code desde}: débitos menos créditos.
     */
    BigDecimal findSaldoAnterior(int negocioId, MayorFiltro filtro);

    List<MayorDiscriminadoFila> findDetalleDiscriminado(
            int negocioId, MayorDiscriminadoFiltro filtro);

    /**
     * Fechas del rango (y {@code desde - 1} para el saldo anterior) sin una
     * cotización disponible para el par elegido. Vacío si no se convierte.
     */
    List<LocalDate> findFechasSinCotizacion(int negocioId, MayorFiltro filtro);

    List<LocalDate> findFechasSinCotizacionDiscriminado(
            int negocioId, MayorDiscriminadoFiltro filtro);

}
