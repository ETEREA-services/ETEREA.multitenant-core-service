package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.kotlin.model.Parametro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ParametroRepository: JpaRepository<Parametro, Int> {

    fun findTopByOrderByParametroIdDesc(): Optional<Parametro?>?

}