package co.edu.unimagdalena.turismo;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class, SecurityException.class })
  @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
  public Map<String, String> bad(Exception e) {
    return Map.of("error", e.getMessage() == null ? "solicitud inválida" : e.getMessage());
  }

  @ExceptionHandler(java.util.NoSuchElementException.class)
  @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
  public Map<String, String> notFound(Exception e) {
    return Map.of("error", e.getMessage() == null ? "no encontrado" : e.getMessage());
  }

  @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
  @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
  public Map<String, String> integrity(Exception e) {
    return Map.of("error", "violación de integridad (revisa campos únicos/disponibilidad)");
  }

  @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
  @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
  public Map<String, String> validation(org.springframework.web.bind.MethodArgumentNotValidException e) {
    String msg = e.getBindingResult().getFieldErrors().stream()
      .map(f -> f.getField() + ": " + f.getDefaultMessage()).findFirst().orElse("campos inválidos (RF-060)");
    return Map.of("error", msg);
  }
}
