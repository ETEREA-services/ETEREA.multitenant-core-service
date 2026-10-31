/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.exception;


import eterea.tenant.service.modules.reserva.legacy.Hotel;
/**
 * @author daniel
 *
 */
public class HotelException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4522058399215532152L;

	public HotelException(Integer hotelId) {
		super("Cannot find Hotel " + hotelId);
	}
}
