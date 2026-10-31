package eterea.tenant.service.modules.tesoreria.cierrecaja.anticipohaberes.infrastructure.persistence.repository;

import eterea.tenant.service.modules.tesoreria.cierrecaja.anticipohaberes.infrastructure.persistence.entity.CierreCajaAnticipoHaberesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCierreCajaAnticipoHaberesRepository extends JpaRepository<CierreCajaAnticipoHaberesEntity, UUID> {
}
