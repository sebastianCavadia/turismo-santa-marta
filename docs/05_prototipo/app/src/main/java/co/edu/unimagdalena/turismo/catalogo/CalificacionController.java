package co.edu.unimagdalena.turismo.catalogo;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/servicios/{id}/calificaciones")
public class CalificacionController {
  private final CalificacionService califs;

  public CalificacionController(CalificacionService c) { this.califs = c; }

  public record CalificarReq(Long turistaId, Long reservaId, int estrellas, String texto) {}

  @PostMapping
  public Calificacion calificar(@PathVariable Long id, @RequestBody CalificarReq req) {
    return califs.calificar(id, req.turistaId(), req.reservaId(), req.estrellas(), req.texto());
  }

  @GetMapping
  public Map<String, Object> listar(@PathVariable Long id,
      @RequestParam(required = false) Integer estrellas) {
    return califs.resumen(id, estrellas);
  }

  public record ResponderReq(String rol, String texto) {}

  @PostMapping("/{califId}/responder")
  public Calificacion responder(@PathVariable Long id, @PathVariable Long califId,
      @RequestBody ResponderReq req) {
    return califs.responder(califId, req.rol(), req.texto());
  }
}
