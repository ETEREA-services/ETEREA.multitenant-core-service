package eterea.tenant.service.modules.ventas.legacy;


import eterea.tenant.service.modules.ventas.legacy.RegistroCae;
public class RegistroCaeException extends RuntimeException {

    public RegistroCaeException(int comprobanteId, int puntoVenta, long numeroComprobante) {
        super("Cannot fund RegistroCae " + comprobanteId + "/" + puntoVenta + "/" + numeroComprobante);
    }

}
