package eterea.tenant.service.tenant.kotlin.exception.view

class SaldoArticuloException(centroStockId: Int, articuloId: String) : RuntimeException("Cannot find SaldoArticulo for CentroStock -> $centroStockId, Articulo -> $articuloId")