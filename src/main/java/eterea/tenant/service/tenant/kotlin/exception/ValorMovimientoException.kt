package eterea.tenant.service.tenant.kotlin.exception

class ValorMovimientoException(valorMovimientoId: Long) : RuntimeException("Cannot find ValorMovimiento $valorMovimientoId")