package eterea.tenant.service.tenant.kotlin.exception

class ArticuloListaPrecioException(articuloId: String) : RuntimeException("ArticuloListaPrecio not found for articuloId: $articuloId")