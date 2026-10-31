package eterea.tenant.service.modules.reserva.legacy;

public class ReservaArticuloException extends RuntimeException {

    public ReservaArticuloException(long reservaArticuloId) {
        super("Cannot find ReservaArticulo " + reservaArticuloId);
    }

}
