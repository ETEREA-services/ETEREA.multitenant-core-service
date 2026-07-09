package eterea.tenant.service.tenant.exception;

public class PosicionIvaException extends RuntimeException {

    public PosicionIvaException(Integer posicionId) {
        super("Cannot find PosicionIva " + posicionId);
    }

}
