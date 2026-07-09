package eterea.tenant.service.tenant.kotlin.exception

class RubroListaPrecioException(rubroId: Long) : RuntimeException("Cannot find Rubro $rubroId")