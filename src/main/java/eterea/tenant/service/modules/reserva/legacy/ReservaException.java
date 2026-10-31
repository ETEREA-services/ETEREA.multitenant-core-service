package eterea.tenant.service.modules.reserva.legacy;

public class ReservaException extends RuntimeException {

    public ReservaException(long reservaId) {
        super("Cannot find Reserva " + reservaId);
    }

}
