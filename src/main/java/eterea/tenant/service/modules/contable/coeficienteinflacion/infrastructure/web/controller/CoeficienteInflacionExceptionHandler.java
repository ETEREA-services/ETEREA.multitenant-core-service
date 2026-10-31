package eterea.tenant.service.modules.contable.coeficienteinflacion.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.coeficienteinflacion.domain.exception.CoeficienteInflacionInvalidaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CoeficienteInflacionController.class)
public class CoeficienteInflacionExceptionHandler {

    @ExceptionHandler(CoeficienteInflacionInvalidaException.class)
    public ResponseEntity<ProblemDetail> handle(
            CoeficienteInflacionInvalidaException exception) {
        HttpStatus status = statusFor(exception.getCodigo());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problem.setTitle(exception.getCodigo());
        problem.setProperty("codigo", exception.getCodigo());
        return ResponseEntity.status(status).body(problem);
    }

    private HttpStatus statusFor(String codigo) {
        return switch (codigo) {
            case "COEFICIENTE_CONFLICTO" -> HttpStatus.CONFLICT;
            case "COEFICIENTE_SIN_PERMISO" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

}
