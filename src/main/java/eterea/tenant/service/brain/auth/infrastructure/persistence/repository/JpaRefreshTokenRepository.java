package eterea.tenant.service.brain.auth.infrastructure.persistence.repository;

import eterea.tenant.service.brain.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);

    List<RefreshTokenEntity> findAllByUsuarioIdAndRevokedAtIsNull(String usuarioId);

    long deleteByExpiresAtBefore(OffsetDateTime fecha);

}
