package eterea.tenant.service.modules.contable.ejercicio.infrastructure.web.controller;

import eterea.tenant.service.modules.contable.ejercicio.domain.exception.EjercicioInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = EjercicioController.class)
public class EjercicioExceptionHandler {

    @ExceptionHandler(EjercicioInvalidoException.class)
    public ResponseEntity<ProblemDetail> handle(EjercicioInvalidoException exception) {
        HttpStatus status = statusFor(exception.getCodigo());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problem.setTitle(exception.getCodigo());
        problem.setProperty("codigo", exception.getCodigo());
        return ResponseEntity.status(status).body(problem);
    }

    private HttpStatus statusFor(String codigo) {
        return switch (codigo) {
            case "EJERCICIO_NO_EXISTE" -> HttpStatus.NOT_FOUND;
            case "EJERCICIO_SOLAPADO",
                 "EJERCICIO_CON_MOVIMIENTOS",
                 "EJERCICIO_NO_ES_ULTIMO",
                 "EJERCICIO_YA_COMENZO",
                 "EJERCICIO_BLOQUEADO" -> HttpStatus.CONFLICT;
            case "EJERCICIO_SIN_PERMISO" -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

}
