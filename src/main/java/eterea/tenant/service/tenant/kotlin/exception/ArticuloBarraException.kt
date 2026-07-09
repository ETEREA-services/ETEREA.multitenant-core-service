package eterea.tenant.service.tenant.kotlin.exception

class ArticuloBarraException(codigobarras: String) : RuntimeException("Cannot found ArticuloBarra with code $codigobarras")
