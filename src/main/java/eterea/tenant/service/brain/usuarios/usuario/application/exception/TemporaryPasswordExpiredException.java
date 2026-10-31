package eterea.tenant.service.brain.usuarios.usuario.application.exception;

/** La contraseña temporal de un blanqueo ya venció y no permite iniciar sesión. */
public class TemporaryPasswordExpiredException extends UsuarioBrainException {

    public TemporaryPasswordExpiredException() {
        super("La contraseña temporal venció. Pedí a un administrador que la blanquee nuevamente.");
    }
}
