package eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.persistence.repository;

import eterea.tenant.service.tenant.hexagonal.cierrecaja.anticipohaberes.infrastructure.persistence.entity.CierreCajaAnticipoHaberesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCierreCajaAnticipoHaberesRepository extends JpaRepository<CierreCajaAnticipoHaberesEntity, UUID> {
}
