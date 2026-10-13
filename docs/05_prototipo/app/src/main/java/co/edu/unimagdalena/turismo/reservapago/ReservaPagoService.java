package co.edu.unimagdalena.turismo.reservapago;

import co.edu.unimagdalena.turismo.catalogo.*;
import co.edu.unimagdalena.turismo.identity.NotificacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.UUID;

// Capa service dentro del microservicio reserva-pago (estados v3 RF048:
// PENDIENTE, CONFIRMADA, RECHAZADA, CANCELADA, COMPLETADA).
// Pago demo instantáneo (mock pasarela RF043) o comprobante adjunto validado por PRE (RF044).
// En C-2 el mock se reemplaza por WompiProvider (Adapter) sin tocar el núcleo.
@Service
public class ReservaPagoService {
  private final ReservaRepository reservas;
  private final PagoRepository pagos;
  private final AuditoriaRepository auditoria;
  private final ServicioRepository servicios;
  private final DisponibilidadRepository disp;
  private final NotificacionService notificaciones;

  public ReservaPagoService(ReservaRepository r, PagoRepository p, AuditoriaRepository a,
      ServicioRepository s, DisponibilidadRepository d, NotificacionService n) {
    this.reservas = r; this.pagos = p; this.auditoria = a; this.servicios = s; this.disp = d;
    this.notificaciones = n;
  }

  @Transactional
  public Reserva pagarDemo(Long servicioId, Long turistaId, LocalDate fecha, String hora, int personas) {
    var s = servicios.findById(servicioId).orElseThrow(() -> new IllegalArgumentException("Servicio no existe"));
    if (personas <= 0) throw new IllegalArgumentException("Personas debe ser > 0");
    if (fecha == null || fecha.isBefore(LocalDate.now()))
      throw new IllegalArgumentException("Fecha inválida: no se reservan fechas pasadas (RF066)");
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
    r.estado = "PENDIENTE"; // RF036/RF048 v3
    r.totalCop = s.precioCop * personas;
    r = reservas.save(r);
    audit("reserva", r.id, null, "PENDIENTE", r.turistaId);

    // Botón demo: pago mock instantáneo (RF043 simulado). La reserva queda PENDIENTE
    // hasta que el PRE la confirme (RF038) o valide el comprobante (RF044).
    Pago p = new Pago();
    p.reservaId = r.id;
    p.reference = "DEMO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    p.estado = "PAGADA";
    p.modo = "DEMO";
    pagos.save(p);
    audit("pago", p.id, null, "PAGADA", r.turistaId);

    d.cupoOcupado += personas; // RF035 (cupos = totales - reservados)
    disp.save(d);
    notificaciones.notificar(s.prestadorId, "RESERVA_NUEVA",
      "Nueva solicitud #" + r.id + " en " + s.nombre + " (correo real en C-2)");
    return r;
  }

  // RF044: vía manual — el Turista adjunta comprobante/transferencia, el PRE lo valida.
  @Transactional
  public Pago adjuntarComprobante(Long reservaId, Long turistaId, String referencia) {
    Reserva r = reservas.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
    if (!"PENDIENTE".equals(r.estado)) throw new IllegalStateException("Solo en PENDIENTE se adjunta comprobante");
    if (pagos.findByReservaId(reservaId).filter(p -> "PAGADA".equals(p.estado)).isPresent())
      throw new IllegalStateException("Ya tiene pago aprobado");
    Pago p = pagos.findByReservaId(reservaId).orElseGet(Pago::new);
    p.reservaId = reservaId;
    p.reference = (referencia == null || referencia.isBlank())
      ? "MANUAL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase() : referencia.trim();
    p.estado = "EN_VERIFICACION";
    p.modo = "COMPROBANTE";
    p = pagos.save(p);
    audit("pago", p.id, null, "EN_VERIFICACION", turistaId);
    return p;
  }

  @Transactional
  public Reserva validarPago(Long reservaId, String rol) {
    if (!"PRE".equalsIgnoreCase(rol) && !"ADM".equalsIgnoreCase(rol))
      throw new SecurityException("Solo PRE/ADM valida pagos");
    Reserva r = reservas.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
    Pago p = pagos.findByReservaId(reservaId)
      .orElseThrow(() -> new IllegalArgumentException("Sin comprobante adjunto"));
    if (!"EN_VERIFICACION".equals(p.estado)) throw new IllegalStateException("Nada por validar");
    p.estado = "PAGADA";
    pagos.save(p);
    audit("pago", p.id, "EN_VERIFICACION", "PAGADA", null);
    return r;
  }

  @Transactional
  public Reserva confirmar(Long reservaId, String rol) {
    if (!"PRE".equalsIgnoreCase(rol) && !"ADM".equalsIgnoreCase(rol))
      throw new SecurityException("Solo PRE/ADM confirma");
    Reserva r = reservas.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
    if (!"PENDIENTE".equals(r.estado)) throw new IllegalStateException("Solo se confirma si PENDIENTE (RF038)");
    boolean pagada = pagos.findByReservaId(reservaId).map(p -> "PAGADA".equals(p.estado)).orElse(false);
    if (!pagada) throw new IllegalStateException("Solo se confirma con pago aprobado o validado (RF044)");
    r.estado = "CONFIRMADA";
    r = reservas.save(r);
    audit("reserva", r.id, "PENDIENTE", "CONFIRMADA", null);
    notificaciones.notificar(r.turistaId, "RESERVA_CONFIRMADA",
      "Tu reserva #" + r.id + " fue confirmada (correo real en C-2)");
    return r;
  }

  @Transactional
  public Reserva rechazar(Long reservaId, String rol) {
    if (!"PRE".equalsIgnoreCase(rol) && !"ADM".equalsIgnoreCase(rol))
      throw new SecurityException("Solo PRE/ADM rechaza");
    Reserva r = reservas.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
    if (!"PENDIENTE".equals(r.estado)) throw new IllegalStateException("Solo se rechaza si PENDIENTE (RF038)");
    r.estado = "RECHAZADA";
    r = reservas.save(r);
    audit("reserva", r.id, "PENDIENTE", "RECHAZADA", null);
    liberarCupo(r);
    reembolsar(r.id);
    notificaciones.notificar(r.turistaId, "RESERVA_RECHAZADA",
      "Tu reserva #" + r.id + " fue rechazada (correo real en C-2)");
    return r;
  }

  // RF040: cancelar con motivo + política (plazo demo: siempre permite, registra motivo).
  @Transactional
  public Reserva cancelar(Long reservaId, Long usuarioId, String motivo) {
    if (motivo == null || motivo.isBlank()) throw new IllegalArgumentException("Motivo obligatorio (RF040)");
    Reserva r = reservas.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
    if (!"PENDIENTE".equals(r.estado) && !"CONFIRMADA".equals(r.estado))
      throw new IllegalStateException("Solo se cancela si PENDIENTE o CONFIRMADA");
    String antes = r.estado;
    r.estado = "CANCELADA";
    r = reservas.save(r);
    audit("reserva", r.id, antes, "CANCELADA:" + motivo.trim(), usuarioId);
    liberarCupo(r);
    reembolsar(r.id); // RF045 demo: reembolso local si estaba pagada
    notificaciones.notificar(r.turistaId, "RESERVA_CANCELADA",
      "Tu reserva #" + r.id + " fue cancelada: " + motivo.trim());
    return r;
  }

  private void liberarCupo(Reserva r) {
    var d = disp.findByServicioIdAndFecha(r.servicioId, r.fecha).orElse(null);
    if (d != null && d.cupoOcupado >= r.personas) {
      d.cupoOcupado -= r.personas;
      disp.save(d);
    }
  }

  private void reembolsar(Long reservaId) {
    var p = pagos.findByReservaId(reservaId).orElse(null);
    if (p != null && "PAGADA".equals(p.estado)) {
      p.estado = "REEMBOLSADO";
      pagos.save(p);
      audit("pago", p.id, "PAGADA", "REEMBOLSADO", null);
    }
  }

  private void audit(String entidad, Long id, String antes, String despues, Long user) {
    Auditoria a = new Auditoria();
    a.entidad = entidad; a.entidadId = id; a.accion = "cambio-estado";
    a.estadoAntes = antes; a.estadoDespues = despues; a.usuarioId = user;
    auditoria.save(a);
  }
}
