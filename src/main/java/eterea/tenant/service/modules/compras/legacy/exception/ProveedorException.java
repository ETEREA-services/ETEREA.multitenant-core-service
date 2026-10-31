/**
 * 
 */
package eterea.tenant.service.modules.compras.legacy.exception;


import eterea.tenant.service.modules.compras.legacy.Proveedor;
/**
 * @author daniel
 *
 */
public class ProveedorException extends RuntimeException {

	private static final long serialVersionUID = 5119802270092164014L;

	public ProveedorException(Integer proveedorId) {
		super("Cannot find Proveedor " + proveedorId);
	}

}
