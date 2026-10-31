package eterea.tenant.service.brain.auth.application.exception;

public class AdminRequiredException extends RuntimeException {

    public AdminRequiredException() {
        super("El usuario no tiene permisos de administración");
    }

}
