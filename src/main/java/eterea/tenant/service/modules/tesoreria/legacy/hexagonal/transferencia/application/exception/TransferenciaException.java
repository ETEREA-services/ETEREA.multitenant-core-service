package eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.application.exception;


import eterea.tenant.service.modules.tesoreria.legacy.hexagonal.transferencia.domain.model.Transferencia;
public class TransferenciaException extends RuntimeException {

    public TransferenciaException() {
        super("Cannot find Transferencia");
    }

    public TransferenciaException(Integer negocioIdDesde, Integer negocioIdHasta, Long numeroTransferencia) {
        super("Cannot find Transferencia with negocioIdDesde: " + negocioIdDesde
                + ", negocioIdHasta: " + negocioIdHasta
                + ", numeroTransferencia: " + numeroTransferencia);
    }

}
