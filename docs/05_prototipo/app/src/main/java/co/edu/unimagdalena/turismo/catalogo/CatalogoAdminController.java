package co.edu.unimagdalena.turismo.catalogo;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prestador/servicios")
public class CatalogoAdminController {
  private final CatalogoAdminService admin;

  public CatalogoAdminController(CatalogoAdminService a) { this.admin = a; }

  @PostMapping
  public Servicio crear(@Valid @RequestBody CatalogoAdminService.CrearServicioReq req,
      @RequestParam(defaultValue = "2") Long prestadorId) {
    return admin.crear(req, prestadorId);
  }

  @PutMapping("/{id}")
  public Servicio editar(@PathVariable Long id,
      @RequestBody CatalogoAdminService.CrearServicioReq req) {
    return admin.editar(id, req);
  }

  @PostMapping("/{id}/eliminar")
  public Servicio eliminar(@PathVariable Long id) { return admin.eliminar(id); }

  @PostMapping("/{id}/habilitar")
  public Servicio habilitar(@PathVariable Long id) { return admin.habilitar(id); }

  @GetMapping
  public List<Servicio> mios(@RequestParam(defaultValue = "2") Long prestadorId) {
    return admin.mios(prestadorId);
  }
}
