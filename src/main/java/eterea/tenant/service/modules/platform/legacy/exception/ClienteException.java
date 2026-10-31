/**
 * 
 */
package eterea.tenant.service.modules.platform.legacy.exception;

import eterea.tenant.service.modules.platform.legacy.Cliente;
import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class ClienteException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3569157004260674955L;

	public ClienteException(Long clienteId) {
		super(MessageFormat.format("Cannot find Cliente {0}", clienteId));
	}

	public ClienteException(String numeroDocumento) {
		super(MessageFormat.format("Cannot find Cliente {0}", numeroDocumento));
	}

	public ClienteException() {
		super("Cannot find Cliente");
	}
}
