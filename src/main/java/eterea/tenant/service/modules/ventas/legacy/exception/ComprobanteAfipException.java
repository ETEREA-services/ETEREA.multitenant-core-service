/**
 * 
 */
package eterea.tenant.service.modules.ventas.legacy.exception;


import eterea.tenant.service.modules.ventas.legacy.ComprobanteAfip;
/**
 * @author daniel
 *
 */
public class ComprobanteAfipException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1390156488409610663L;

	public ComprobanteAfipException(Integer comprobanteAfipId) {
		super("Cannot find ComprobanteAfip " + comprobanteAfipId);
	}

}
