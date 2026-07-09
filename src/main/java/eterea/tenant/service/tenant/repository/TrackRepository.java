package eterea.tenant.service.tenant.repository;

import eterea.tenant.service.tenant.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, String> {

    Optional<Track> findByUuid(String trackUuid);

}
