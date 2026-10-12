package co.edu.unimagdalena.turismo.reservapago;

import co.edu.unimagdalena.turismo.catalogo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.UUID;

// Capa service dentro del microservicio reserva-pago.
// Pago simulado: el botón "Reservar y pagar (demo)" crea PENDIENTE_PAGO y lo pasa a PAGADA
// en la misma transacción. En C-2 se reemplaza por WompiProvider (Adapter) sin tocar el núcleo.
@Service
public class ReservaPagoService {
  private final ReservaRepository reservas;
  private final PagoRepository pagos;
  private final AuditoriaRepository auditoria;
  private final ServicioRepository servicios;
  private final DisponibilidadRepository disp;

  public ReservaPagoService(ReservaRepository r, PagoRepository p, AuditoriaRepository a,
      ServicioRepository s, DisponibilidadRepository d) {
    this.reservas = r; this.pagos = p; this.auditoria = a; this.servicios = s; this.disp = d;
  }

  @Transactional
  public Reserva pagarDemo(Long servicioId, Long turistaId, LocalDate fecha, String hora, int personas) {
    var s = servicios.findById(servicioId).orElseThrow(() -> new IllegalArgumentException("Servicio no existe"));
    if (personas <= 0) throw new IllegalArgumentException("Personas debe ser > 0");
    var d = disp.findByServicioIdAndFecha(servicioId, fecha)
      .orElseThrow(() -> new IllegalArgumentException("Sin disponibilidad para esa fecha"));
    if (Boolean.TRUE.equals(d.bloqueado)) throw new IllegalStateException("Fecha bloqueada");
    if (d.cuposLibres() < personas) throw new IllegalStateException("Sin disponibilidad suficiente (RF-057)");

    Reserva r = new Reserva();
    r.servicioId = servicioId;
    r.turistaId = turistaId != null ? turistaId : 1L;
    r.fecha = fecha;
    r.hora = (hora == null || hora.isBlank()) ? null : java.time.LocalTime.parse(hora);
    r.personas = personas;
    r.estado = "PENDIENTE_PAGO"; // RF-042
    r.totalCop = s.precioCop * personas;
    r = reservas.save(r);
    audit("reserva", r.id, null, "PENDIENTE_PAGO", r.turistaId);

    // Botón demo: pago falso instantáneo (reemplaza PAG1+PAG2 Wompi en prototipo)
    Pago p = new Pago();
    p.reservaId = r.id;
    p.reference = "DEMO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    p.estado = "PAGADA";
    p.modo = "DEMO";
    pagos.save(p);

    r.estado = "PAGADA"; // RF-104 simulado
    r = reservas.save(r);
    audit("reserva", r.id, "PENDIENTE_PAGO", "PAGADA", r.turistaId);
    audit("pago", p.id, null, "PAGADA", r.turistaId);

    d.cupoOcupado += personas; // RF-055 (RF-073 regla auto)
    disp.save(d);
    return r;
  }

  @Transactional
  public Reserva confirmar(Long reservaId, String rol) {
    if (!"PRE".equalsIgnoreCase(rol) && !"ADM".equalsIgnoreCase(rol))
      throw new SecurityException("Solo PRE/ADM confirma");
    Reserva r = reservas.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
    if (!"PAGADA".equals(r.estado)) throw new IllegalStateException("Solo se confirma si PAGADA (RF-050/105)");
    r.estado = "CONFIRMADA";
    r = reservas.save(r);
    audit("reserva", r.id, "PAGADA", "CONFIRMADA", null);
    return r;
  }

  @Transactional
  public Reserva rechazar(Long reservaId, String rol) {
    if (!"PRE".equalsIgnoreCase(rol) && !"ADM".equalsIgnoreCase(rol))
      throw new SecurityException("Solo PRE/ADM rechaza");
    Reserva r = reservas.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
    if (!"PAGADA".equals(r.estado)) throw new IllegalStateException("Solo se rechaza si PAGADA (RF-052)");
    r.estado = "RECHAZADA";
    r = reservas.save(r);
    audit("reserva", r.id, "PAGADA", "RECHAZADA", null);
    // Libera cupo (RF-056/RF-073) + reembolso demo (RF-106 simulado, sin Wompi)
    var d = disp.findByServicioIdAndFecha(r.servicioId, r.fecha).orElse(null);
    if (d != null && d.cupoOcupado >= r.personas) {
      d.cupoOcupado -= r.personas;
      disp.save(d);
    }
    var p = pagos.findByReservaId(r.id).orElse(null);
    if (p != null) {
      p.estado = "REEMBOLSADO";
      pagos.save(p);
      audit("pago", p.id, "PAGADA", "REEMBOLSADO", null);
    }
    return r;
  }

  private void audit(String entidad, Long id, String antes, String despues, Long user) {
    Auditoria a = new Auditoria();
    a.entidad = entidad; a.entidadId = id; a.accion = "cambio-estado";
    a.estadoAntes = antes; a.estadoDespues = despues; a.usuarioId = user;
    auditoria.save(a);
  }
}
