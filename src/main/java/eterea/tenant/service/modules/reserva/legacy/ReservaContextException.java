package eterea.tenant.service.modules.reserva.legacy;


import eterea.tenant.service.modules.reserva.legacy.ReservaContext;
public class ReservaContextException extends RuntimeException {

    public ReservaContextException(String type, long value) {
        super("Cannot find ReservaContext (" + type + ") -> " + value);
    }

    public ReservaContextException(long reservaContextId) {
        this("", reservaContextId);
    }

}
