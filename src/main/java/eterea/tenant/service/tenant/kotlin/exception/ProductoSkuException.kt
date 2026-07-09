package eterea.tenant.service.tenant.kotlin.exception

class ProductoSkuException(sku: String) : RuntimeException("Cannot find ProductoSku $sku")