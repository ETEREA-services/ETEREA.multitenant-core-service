package eterea.tenant.service.tenant.kotlin.exception

class ValorException(valorId: Int) : RuntimeException("Cannot find Valor $valorId")