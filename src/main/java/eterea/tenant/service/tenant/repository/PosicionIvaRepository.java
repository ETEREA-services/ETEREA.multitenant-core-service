package eterea.tenant.service.tenant.repository;

import eterea.tenant.service.tenant.model.PosicionIva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PosicionIvaRepository extends JpaRepository<PosicionIva, Integer> {

    Optional<PosicionIva> findByPosicionId(Integer posicionId);

}
