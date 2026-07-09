package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.kotlin.model.ConceptoFacturado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ConceptoFacturadoRepository : JpaRepository<ConceptoFacturado, Long> {

    fun findTopByArticuloMovimientoId(articuloMovimientoId: Long): Optional<ConceptoFacturado?>?

}
