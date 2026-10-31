package eterea.tenant.service.modules.platform.legacy.repository;

import eterea.tenant.service.modules.platform.legacy.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByLogin(String login);

    void deleteByLogin(String login);

}
