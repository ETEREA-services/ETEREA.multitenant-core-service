package eterea.tenant.service.modules.platform.legacy.repository;

import eterea.tenant.service.modules.platform.legacy.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, String> {

    Optional<Track> findByUuid(String trackUuid);

}
