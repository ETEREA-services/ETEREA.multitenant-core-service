package eterea.tenant.service.brain.usuario.infrastructure.security;

import eterea.tenant.service.brain.usuario.domain.ports.out.PasswordHashPort;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Argon2PasswordHashAdapter implements PasswordHashPort {

    private final PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String password, String passwordHash) {
        return passwordHash != null && passwordEncoder.matches(password, passwordHash);
    }
}
