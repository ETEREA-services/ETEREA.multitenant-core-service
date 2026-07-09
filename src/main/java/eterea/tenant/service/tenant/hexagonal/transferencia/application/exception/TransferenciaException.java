package eterea.tenant.service.tenant.hexagonal.transferencia.application.exception;

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
