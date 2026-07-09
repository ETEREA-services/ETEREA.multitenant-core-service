package eterea.tenant.service.tenant.kotlin.exception

import java.time.OffsetDateTime

class FeriadoException(fecha: OffsetDateTime) : RuntimeException("Cannot find Feriado $fecha")