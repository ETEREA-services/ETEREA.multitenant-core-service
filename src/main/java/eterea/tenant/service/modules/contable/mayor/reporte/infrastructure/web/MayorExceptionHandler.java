package eterea.tenant.service.modules.contable.mayor.reporte.infrastructure.web;

import eterea.tenant.service.modules.contable.mayor.domain.exception.MayorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Traduce los errores del Mayor al contrato {@link ProblemDetail}.
 */
@RestControllerAdvice
public class MayorExceptionHandler {

    @ExceptionHandler(MayorException.class)
    public ResponseEntity<ProblemDetail> handle(MayorException exception) {
        HttpStatus status = statusFor(exception.getCodigo());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problem.setTitle(exception.getCodigo());
        problem.setProperty("codigo", exception.getCodigo());
        return ResponseEntity.status(status).body(problem);
    }

    private HttpStatus statusFor(String codigo) {
        return switch (codigo) {
            case "MAYOR_RANGO_INVALIDO",
                 "MAYOR_CUENTA_REQUERIDA",
                 "MAYOR_CUENTAS_REQUERIDAS",
                 "MAYOR_MONEDA_INVALIDA" -> HttpStatus.BAD_REQUEST;
            case "MAYOR_SIN_NEGOCIO",
                 "MAYOR_CUENTA_NO_EXISTE" -> HttpStatus.NOT_FOUND;
            case "MAYOR_SIN_PERMISO" -> HttpStatus.FORBIDDEN;
            case "MAYOR_COTIZACION_FALTANTE" -> HttpStatus.UNPROCESSABLE_ENTITY;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
