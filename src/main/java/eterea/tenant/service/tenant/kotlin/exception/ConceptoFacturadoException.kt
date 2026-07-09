package eterea.tenant.service.tenant.kotlin.exception

class ConceptoFacturadoException(articuloMovimientoId: Long) : RuntimeException("Cannot find ConceptoFacturado by ArticuloMovimiento= $articuloMovimientoId")