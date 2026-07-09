package eterea.tenant.service.tenant.exception

class ProductoArticuloException(productoId: Int, articuloId: String) : RuntimeException("Cannot find ProductoArticulo $productoId/$articuloId")