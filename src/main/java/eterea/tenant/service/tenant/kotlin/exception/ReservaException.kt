package eterea.tenant.service.tenant.kotlin.exception

class ReservaException(reservaId: Long) : RuntimeException("Cannot find Reserva $reservaId")