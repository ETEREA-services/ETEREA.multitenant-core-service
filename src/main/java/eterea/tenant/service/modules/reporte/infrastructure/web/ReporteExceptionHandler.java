package eterea.tenant.service.modules.reporte.infrastructure.web;

import eterea.tenant.service.modules.reporte.api.ReporteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Traduce los errores de reportes al contrato {@link ProblemDetail}.
 */
@RestControllerAdvice
public class ReporteExceptionHandler {

    @ExceptionHandler(ReporteException.class)
    public ResponseEntity<ProblemDetail> handle(ReporteException exception) {
        HttpStatus status = statusFor(exception.getCodigo());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problem.setTitle(exception.getCodigo());
        problem.setProperty("codigo", exception.getCodigo());
        return ResponseEntity.status(status).body(problem);
    }

    private HttpStatus statusFor(String codigo) {
        return switch (codigo) {
            case "REPORTE_SIN_NEGOCIO" -> HttpStatus.FORBIDDEN;
            case "REPORTE_GENERACION_FALLIDA" -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

}
