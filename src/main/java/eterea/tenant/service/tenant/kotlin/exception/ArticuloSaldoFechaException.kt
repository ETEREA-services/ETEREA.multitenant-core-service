package eterea.tenant.service.tenant.kotlin.exception

import java.time.OffsetDateTime

class ArticuloSaldoFechaException(centroStockId: Int, articuloId: String, fecha: OffsetDateTime) : RuntimeException("Cannot find ArticuloSaldoFecha for CentroStock -> $centroStockId, Articulo -> $articuloId, Fecha -> $fecha")