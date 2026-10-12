package co.edu.unimagdalena.turismo.reservapago;

import co.edu.unimagdalena.turismo.catalogo.ServicioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

// RES4: reservas recibidas por prestador (RF-048/049), PAGADA primero.
@RestController
@RequestMapping("/api/prestador/reservas")
public class PrestadorReservaController {
  private final ReservaRepository reservas;
  private final ServicioRepository servicios;

  public PrestadorReservaController(ReservaRepository r, ServicioRepository s) {
    this.reservas = r; this.servicios = s;
  }

  @GetMapping
  public List<Map<String, Object>> recibidas(@RequestParam(defaultValue = "2") Long prestadorId) {
    var porServicio = new HashMap<Long, String>();
    for (var s : servicios.findAll())
      if (prestadorId.equals(s.prestadorId)) porServicio.put(s.id, s.nombre);
    var out = new ArrayList<Map<String, Object>>();
    for (var r : reservas.findAll()) {
      if (!porServicio.containsKey(r.servicioId)) continue;
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("id", r.id);
      m.put("servicio", porServicio.get(r.servicioId));
      m.put("fecha", r.fecha.toString());
      m.put("personas", r.personas);
      m.put("totalCop", r.totalCop);
      m.put("estado", r.estado);
      m.put("confirmable", "PAGADA".equals(r.estado));
      out.add(m);
    }
    out.sort((a, b) -> {
      boolean pa = (boolean) a.get("confirmable"), pb = (boolean) b.get("confirmable");
      if (pa != pb) return pa ? -1 : 1;
      return Long.compare((Long) b.get("id"), (Long) a.get("id"));
    });
    return out;
  }
}
