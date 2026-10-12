package co.edu.unimagdalena.turismo.catalogo;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

// Capa service dentro de catalogo-service (micro + capas).
// SER1-4 + DIS inicial. Validación RF-060/062. Borrado lógico SER3 respeta reservas existentes.
@org.springframework.stereotype.Service
@org.springframework.transaction.annotation.Transactional
public class CatalogoAdminService {
  private final ServicioRepository servicios;
  private final DisponibilidadRepository disp;

  public CatalogoAdminService(ServicioRepository s, DisponibilidadRepository d) {
    this.servicios = s; this.disp = d;
  }

  public record CrearServicioReq(
    @NotBlank String nombre, String descripcion,
    @NotBlank String categoria, @NotBlank String zona,
    @NotNull @Min(0) Long precioCop, @NotBlank String tipo,
    @Min(1) int cupoTotal) {}

  public Servicio crear(CrearServicioReq req, Long prestadorId) {
    Servicio s = new Servicio();
    s.nombre = req.nombre().trim();
    s.descripcion = req.descripcion();
    s.categoria = req.categoria();
    s.zona = req.zona();
    s.precioCop = req.precioCop();
    s.tipo = req.tipo();
    s.habilitado = true;
    s.prestadorId = prestadorId != null ? prestadorId : 2L;
    s.creadoEn = java.time.LocalDateTime.now();
    s = servicios.save(s);
    for (int i = 0; i < 30; i++) {
      Disponibilidad d = new Disponibilidad();
      d.servicioId = s.id;
      d.fecha = LocalDate.now().plusDays(i);
      d.cupoTotal = req.cupoTotal();
      d.cupoOcupado = 0;
      d.bloqueado = false;
      disp.save(d);
    }
    return s;
  }

  public Servicio editar(Long id, CrearServicioReq req) {
    Servicio s = servicios.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Servicio no existe"));
    if (req.nombre() != null && !req.nombre().isBlank()) s.nombre = req.nombre().trim();
    if (req.descripcion() != null) s.descripcion = req.descripcion();
    if (req.categoria() != null && !req.categoria().isBlank()) s.categoria = req.categoria();
    if (req.zona() != null && !req.zona().isBlank()) s.zona = req.zona();
    if (req.precioCop() != null) s.precioCop = req.precioCop();
    if (req.tipo() != null && !req.tipo().isBlank()) s.tipo = req.tipo();
    return servicios.save(s);
  }

  public Servicio eliminar(Long id) {
    Servicio s = servicios.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Servicio no existe"));
    s.habilitado = false;
    return servicios.save(s);
  }

  public Servicio habilitar(Long id) {
    Servicio s = servicios.findById(id).orElseThrow(() -> new java.util.NoSuchElementException("Servicio no existe"));
    if (s.nombre == null || s.categoria == null || s.precioCop == null) throw new IllegalStateException("No cumple validación RF-060");
    s.habilitado = true;
    return servicios.save(s);
  }

  public List<Servicio> mios(Long prestadorId) {
    return servicios.findAll().stream()
      .filter(s -> prestadorId == null || prestadorId.equals(s.prestadorId)).toList();
  }
}
