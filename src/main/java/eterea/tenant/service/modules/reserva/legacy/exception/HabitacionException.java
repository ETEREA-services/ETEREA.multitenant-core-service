/**
 * 
 */
package eterea.tenant.service.modules.reserva.legacy.exception;

import eterea.tenant.service.modules.reserva.legacy.Habitacion;
import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class HabitacionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3854243777561959702L;

	public HabitacionException(Integer numero) {
		super(MessageFormat.format("Cannot find Habitacion {}", numero));
	}

}
