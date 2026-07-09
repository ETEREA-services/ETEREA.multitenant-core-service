package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.kotlin.model.ArticuloBarra
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import java.util.Optional

interface ArticuloBarraRepository : JpaRepository<ArticuloBarra, String> {

    fun findAllByArticuloId(articuloId: String): List<ArticuloBarra?>?

    fun findByCodigoBarras(codigoBarras: String): Optional<ArticuloBarra?>?

    @Modifying
    fun deleteByCodigoBarras(codigoBarras: String)

}