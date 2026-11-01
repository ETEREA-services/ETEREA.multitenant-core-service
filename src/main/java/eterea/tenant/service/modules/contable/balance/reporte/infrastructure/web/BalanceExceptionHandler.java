package eterea.tenant.service.modules.contable.balance.reporte.infrastructure.web;

import eterea.tenant.service.modules.contable.balance.domain.exception.BalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Traduce los errores del balance al contrato {@link ProblemDetail}.
 */
@RestControllerAdvice
public class BalanceExceptionHandler {

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<ProblemDetail> handle(BalanceException exception) {
        HttpStatus status = statusFor(exception.getCodigo());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problem.setTitle(exception.getCodigo());
        problem.setProperty("codigo", exception.getCodigo());
        return ResponseEntity.status(status).body(problem);
    }

    private HttpStatus statusFor(String codigo) {
        return switch (codigo) {
            case "BALANCE_RANGO_INVALIDO", "BALANCE_MONEDA_INVALIDA" -> HttpStatus.BAD_REQUEST;
            case "BALANCE_SIN_NEGOCIO" -> HttpStatus.NOT_FOUND;
            case "BALANCE_COTIZACION_FALTANTE" -> HttpStatus.UNPROCESSABLE_ENTITY;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
