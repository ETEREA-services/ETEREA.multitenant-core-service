package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.kotlin.model.RubroListaPrecio
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface RubroListaPrecioRepository : JpaRepository<RubroListaPrecio, UUID> {

    fun findAllByOrderByRubroDescripcion(): List<RubroListaPrecio>

    fun findAllByPublicarOrderByRubroDescripcion(publicar: Byte): List<RubroListaPrecio>

    fun findByRubroId(rubroId: Long?): Optional<RubroListaPrecio>

}