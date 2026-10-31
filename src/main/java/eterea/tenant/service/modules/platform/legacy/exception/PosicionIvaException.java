package eterea.tenant.service.modules.platform.legacy.exception;


import eterea.tenant.service.modules.platform.legacy.PosicionIva;
public class PosicionIvaException extends RuntimeException {

    public PosicionIvaException(Integer posicionId) {
        super("Cannot find PosicionIva " + posicionId);
    }

}
