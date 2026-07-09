package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.kotlin.model.FacturaRendicionDetalle
import org.springframework.data.jpa.repository.JpaRepository

interface FacturaRendicionDetalleRepository : JpaRepository<FacturaRendicionDetalle, Long> {

    fun findAllByProveedorId(proveedorId: Long): List<FacturaRendicionDetalle?>?

}