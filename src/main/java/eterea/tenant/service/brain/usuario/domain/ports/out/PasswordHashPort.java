package eterea.tenant.service.brain.usuario.domain.ports.out;

public interface PasswordHashPort {
    String hash(String password);
    boolean matches(String password, String passwordHash);
}
