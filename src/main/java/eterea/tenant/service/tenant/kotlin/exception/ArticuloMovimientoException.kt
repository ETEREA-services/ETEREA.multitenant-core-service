package eterea.tenant.service.tenant.kotlin.exception

class ArticuloMovimientoException(articuloMovimientoId: Long) : RuntimeException("Cannot find ArticuloMovimiento $articuloMovimientoId")