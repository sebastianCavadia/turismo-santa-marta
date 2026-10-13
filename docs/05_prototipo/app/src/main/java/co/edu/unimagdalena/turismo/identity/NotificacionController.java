package co.edu.unimagdalena.turismo.identity;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
  private final NotificacionService notificaciones;

  public NotificacionController(NotificacionService n) { this.notificaciones = n; }

  @GetMapping
  public List<Notificacion> mis(@RequestParam Long usuarioId) {
    return notificaciones.de(usuarioId);
  }
}
