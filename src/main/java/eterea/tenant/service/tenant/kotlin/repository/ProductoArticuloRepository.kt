package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.kotlin.model.ProductoArticulo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProductoArticuloRepository : JpaRepository<ProductoArticulo, Long> {

    fun findAllByProductoId(productoId: Int?): List<ProductoArticulo?>?

    fun findByProductoIdAndArticuloId(productoId: Int?, articuloId: String?): Optional<ProductoArticulo?>?

}