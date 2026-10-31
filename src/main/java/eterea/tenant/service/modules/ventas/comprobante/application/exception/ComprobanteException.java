/**
 * 
 */
package eterea.tenant.service.modules.ventas.comprobante.application.exception;


import eterea.tenant.service.modules.ventas.comprobante.domain.model.Comprobante;
/**
 * @author daniel
 *
 */
public class ComprobanteException extends RuntimeException {

	public ComprobanteException() {
		super("Cannot find Comprobante");
	}

	public ComprobanteException(Integer comprobanteId) {
		super("Cannot find Comprobante " + comprobanteId);
	}

}
