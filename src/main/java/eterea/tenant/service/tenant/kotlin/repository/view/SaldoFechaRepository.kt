package eterea.tenant.service.tenant.kotlin.repository.view

import eterea.tenant.service.tenant.kotlin.model.view.SaldoFecha
import org.springframework.data.jpa.repository.JpaRepository
import java.time.OffsetDateTime
import java.util.Optional

interface SaldoFechaRepository : JpaRepository<SaldoFecha, String> {

    fun findAllByCentroStockIdAndFechaAndArticuloIdIn(
        centroStockId: Int,
        fechaMovimiento: OffsetDateTime,
        articuloIds: MutableList<String>
    ): List<SaldoFecha>

    fun findByCentroStockIdAndArticuloIdAndFecha(
        centroStockId: Int,
        articuloId: String,
        fechaMovimiento: OffsetDateTime
    ): Optional<SaldoFecha?>?

}