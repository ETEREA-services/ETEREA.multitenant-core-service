package eterea.tenant.service.tenant.kotlin.exception

class ArticuloCentroException(centroStockId: Int, articuloId: String) : RuntimeException("Cannot find ArticuloCentro for CentroStock -> $centroStockId, Articulo -> $articuloId")