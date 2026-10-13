package co.edu.unimagdalena.turismo.reservapago;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
  private final ReservaPagoService service;
  private final ReservaRepository reservas;
  private final PagoRepository pagos;

  public ReservaController(ReservaPagoService s, ReservaRepository r, PagoRepository p) {
    this.service = s; this.reservas = r; this.pagos = p;
  }

  public record PagarDemoReq(Long servicioId, Long turistaId, String fecha, String hora, int personas) {}

  // RF036: solicitar sin pagar (luego mock o comprobante RF044)
  @PostMapping("/solicitar")
  public Reserva solicitar(@RequestBody PagarDemoReq req) {
    return service.solicitar(req.servicioId(), req.turistaId(),
      LocalDate.parse(req.fecha()), req.hora(), req.personas());
  }

  // Botón único demo: RES1 + PAG falso instantáneo
  @PostMapping("/pagar-demo")
  public Map<String, Object> pagarDemo(@RequestBody PagarDemoReq req) {
    Reserva r = service.pagarDemo(req.servicioId(), req.turistaId(),
      LocalDate.parse(req.fecha()), req.hora(), req.personas());
    var p = pagos.findByReservaId(r.id).orElseThrow();
    return Map.of("reserva", r, "pago", p,
      "comprobante", Map.of("reference", p.reference, "estado", p.estado, "nota", "DEMO sin PAN/CVV"));
  }

  @PostMapping("/{id}/confirmar")
  public Reserva confirmar(@PathVariable Long id, @RequestParam(defaultValue = "PRE") String rol) {
    return service.confirmar(id, rol);
  }

  @PostMapping("/{id}/rechazar")
  public Reserva rechazar(@PathVariable Long id, @RequestParam(defaultValue = "PRE") String rol) {
    return service.rechazar(id, rol);
  }

  public record CancelarReq(Long usuarioId, String motivo) {}

  @PostMapping("/{id}/cancelar")
  public Reserva cancelar(@PathVariable Long id, @RequestBody CancelarReq req) {
    return service.cancelar(id, req.usuarioId(), req.motivo());
  }

  public record ComprobanteReq(Long turistaId, String referencia) {}

  @PostMapping("/{id}/comprobante")
  public Pago comprobante(@PathVariable Long id, @RequestBody ComprobanteReq req) {
    return service.adjuntarComprobante(id, req.turistaId(), req.referencia());
  }

  @PostMapping("/{id}/validar-pago")
  public Reserva validarPago(@PathVariable Long id, @RequestParam(defaultValue = "PRE") String rol) {
    return service.validarPago(id, rol);
  }

  @GetMapping("/mis")
  public Object mis(@RequestParam(defaultValue = "1") Long turistaId,
      @RequestParam(defaultValue = "false") boolean agrupado) {
    var todas = reservas.findByTuristaIdOrderByIdDesc(turistaId);
    if (!agrupado) return todas;
    // RF041: historial clasificado
    Map<String, Object> g = new LinkedHashMap<>();
    g.put("activas", todas.stream().filter(r -> "PENDIENTE".equals(r.estado) || "CONFIRMADA".equals(r.estado)).toList());
    g.put("completadas", todas.stream().filter(r -> "COMPLETADA".equals(r.estado)).toList());
    g.put("rechazadas", todas.stream().filter(r -> "RECHAZADA".equals(r.estado)).toList());
    g.put("canceladas", todas.stream().filter(r -> "CANCELADA".equals(r.estado)).toList());
    return g;
  }

  @GetMapping("/{id}")
  public Map<String, Object> detalle(@PathVariable Long id) {
    var r = reservas.findById(id).orElseThrow();
    var p = pagos.findByReservaId(id).orElse(null);
    return Map.of("reserva", r, "pago", p == null ? Map.of() : p);
  }
}
