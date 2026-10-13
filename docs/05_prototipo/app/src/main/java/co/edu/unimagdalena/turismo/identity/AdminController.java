package co.edu.unimagdalena.turismo.identity;

import co.edu.unimagdalena.turismo.catalogo.ServicioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

// RF091-100 parcial demo: pendientes + validación (resto: buscar/rol/bitácora en C-2 completo).
@RestController
@RequestMapping("/api/admin")
public class AdminController {
  private final AuthService auth;
  private final ServicioRepository servicios;
  private final NotificacionService notificaciones;

  public AdminController(AuthService a, ServicioRepository s, NotificacionService n) {
    this.auth = a; this.servicios = s; this.notificaciones = n;
  }

  private static void exigirAdm(String rol) {
    if (!"ADM".equalsIgnoreCase(rol)) throw new SecurityException("Solo ADM");
  }

  @GetMapping("/pendientes")
  public List<Usuario> pendientes(@RequestParam(defaultValue = "ADM") String rol) {
    exigirAdm(rol);
    return auth.pendientes();
  }

  @PostMapping("/validar-prestador")
  public Map<String, Object> validar(@RequestParam Long prestadorId,
      @RequestParam(defaultValue = "ADM") String rol) {
    exigirAdm(rol);
    Usuario u = auth.validarPrestador(prestadorId);
    int n = 0;
    for (var s : servicios.findAll()) {
      if (prestadorId.equals(s.prestadorId) && Boolean.FALSE.equals(s.validado)) {
        s.validado = true;
        servicios.save(s);
        n++;
      }
    }
    notificaciones.notificar(prestadorId, "VALIDACION",
      "Documentación aprobada: tus " + n + " ofertas ya son públicas (correo real en C-2)");
    return Map.of("id", u.id, "validado", u.validado, "serviciosPublicados", n,
      "nota", "Ofertas ahora públicas (RF085). Notificación mock al prestador (correo en C-2).");
  }
}
