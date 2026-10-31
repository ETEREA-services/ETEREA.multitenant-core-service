package eterea.tenant.service.modules.contable.cuenta.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.cuenta.domain.exception.CuentaInvalidaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CuentaController.class)
public class CuentaExceptionHandler {

    @ExceptionHandler(CuentaInvalidaException.class)
    public ResponseEntity<ProblemDetail> handle(CuentaInvalidaException exception) {
        HttpStatus status = statusFor(exception.getCodigo());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problem.setTitle(exception.getCodigo());
        problem.setProperty("codigo", exception.getCodigo());
        return ResponseEntity.status(status).body(problem);
    }

    private HttpStatus statusFor(String codigo) {
        return switch (codigo) {
            case "CUENTA_NO_EXISTE" -> HttpStatus.NOT_FOUND;
            case "CUENTA_YA_EXISTE",
                 "CUENTA_PADRE_FALTANTE",
                 "CUENTA_PADRE_CON_MOVIMIENTOS",
                 "CUENTA_CON_HIJOS",
                 "CUENTA_CON_MOVIMIENTOS",
                 "CUENTA_CODIGO_INMUTABLE" -> HttpStatus.CONFLICT;
            case "CUENTA_SIN_PERMISO" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
