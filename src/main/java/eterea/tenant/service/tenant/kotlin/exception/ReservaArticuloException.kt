package eterea.tenant.service.tenant.kotlin.exception

class ReservaArticuloException(reservaArticuloId: Long) : RuntimeException("Cannot find ReservaArticulo $reservaArticuloId")