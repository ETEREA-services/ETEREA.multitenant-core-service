package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.kotlin.model.StockMovimiento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface StockMovimientoRepository : JpaRepository<StockMovimiento, Long> {

    fun findFirstByComprobanteIdOrderByStockMovimientoIdDesc(comprobanteId: Int): Optional<StockMovimiento?>?
    fun findByStockMovimientoId(stockMovimientoId: Long): Optional<StockMovimiento?>?

}