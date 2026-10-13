package co.edu.unimagdalena.turismo.catalogo;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/servicios")
public class CatalogoController {
  private final ServicioRepository servicios;
  private final DisponibilidadRepository disp;
  private final CalificacionService califs;

  public CatalogoController(ServicioRepository s, DisponibilidadRepository d, CalificacionService c) {
    this.servicios = s; this.disp = d; this.califs = c;
  }

  // EXP1: buscar, filtrar y ordenar (filtros como parámetros, no CU separado)
  @GetMapping
  public Page<Servicio> buscar(
      @RequestParam(required = false) String q,
      @RequestParam(required = false) String categoria,
      @RequestParam(required = false) String zona,
      @RequestParam(required = false) String tipo,
      @RequestParam(required = false) Long maxPrecio,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort) {
    Pageable pageable = "precio".equals(sort)
      ? PageRequest.of(page, size, Sort.by("precioCop"))
      : PageRequest.of(page, size, Sort.by("id"));
    return servicios.buscar(empty(q), empty(categoria), empty(zona), empty(tipo), maxPrecio, pageable);
  }

  // EXP2: detalle + disponibilidad + alerta congestión + promedio mock
  @GetMapping("/{id}")
  public Map<String, Object> detalle(@RequestParam(required = false) String fecha, @PathVariable Long id) {
    Servicio s = servicios.findById(id).orElseThrow(() -> new NoSuchElementException("Servicio no existe"));
    LocalDate f = fecha != null ? LocalDate.parse(fecha) : LocalDate.now().plusDays(1);
    var d = disp.findByServicioIdAndFecha(id, f).orElse(null);
    int libres = d != null ? d.cuposLibres() : -1;
    boolean alerta = d != null && d.cupoTotal > 0 && ((double) d.cupoOcupado / d.cupoTotal) >= 0.8;
    Map<String, Object> out = new LinkedHashMap<>();
    out.put("servicio", s);
    out.put("fecha", f.toString());
    out.put("cuposLibres", libres);
    out.put("alertaCongestion", alerta); // EXP3 <<extend>> RF-021
    out.put("buenasPracticas", "Si es natural/ecológico: usa agua y energía con moderación"); // EXP4 RF-022/090
    out.put("promedio", califs.total(id) == 0 ? null : califs.promedio(id)); // RF080 real
    out.put("totalResenas", califs.total(id));
    out.put("notaResenas", califs.total(id) == 0
      ? "Sin calificaciones todavía (solo COMPLETADA, 1x por reserva)"
      : "Promedio de " + califs.total(id) + " valoraciones");
    return out;
  }

  private static String empty(String v) { return (v == null || v.isBlank()) ? null : v; }
}
