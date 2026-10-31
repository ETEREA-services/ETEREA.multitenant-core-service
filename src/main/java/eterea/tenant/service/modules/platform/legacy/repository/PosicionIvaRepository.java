package eterea.tenant.service.modules.platform.legacy.repository;

import eterea.tenant.service.modules.platform.legacy.PosicionIva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PosicionIvaRepository extends JpaRepository<PosicionIva, Integer> {

    Optional<PosicionIva> findByPosicionId(Integer posicionId);

}
