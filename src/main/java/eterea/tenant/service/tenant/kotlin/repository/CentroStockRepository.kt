package eterea.tenant.service.tenant.kotlin.repository

import eterea.tenant.service.tenant.kotlin.model.CentroStock
import org.springframework.data.jpa.repository.JpaRepository

interface CentroStockRepository : JpaRepository<CentroStock, Int> {
}