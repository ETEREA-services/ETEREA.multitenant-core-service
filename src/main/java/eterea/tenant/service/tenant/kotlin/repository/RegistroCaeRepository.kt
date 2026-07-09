package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.model.RegistroCae
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RegistroCaeRepository : JpaRepository<RegistroCae, Long> {
    fun findByComprobanteIdAndPuntoVentaAndNumeroComprobante(comprobanteId: Int, puntoVenta: Int, numeroComprobante: Long): Optional<RegistroCae?>?

}