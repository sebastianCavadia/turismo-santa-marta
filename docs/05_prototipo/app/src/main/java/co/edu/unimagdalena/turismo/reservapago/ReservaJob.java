package co.edu.unimagdalena.turismo.reservapago;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Job demo (RF049 + RF056 v3).
// PENDIENTE sin pago aprobado y vieja → CANCELADA + libera cupo (expiración 15min demo).
// CONFIRMADA con fecha pasada → COMPLETADA (habilita calificar en C-2).
@Component
public class ReservaJob {
  private final ReservaRepository reservas;
  private final AuditoriaRepository auditoria;
  private final PagoRepository pagos;
  private final int timeoutMin;

  public ReservaJob(ReservaRepository r, AuditoriaRepository a, PagoRepository p,
      @Value("${prototipo.pago-timeout-min:15}") int timeoutMin) {
    this.reservas = r; this.auditoria = a; this.pagos = p; this.timeoutMin = timeoutMin;
  }

  @Scheduled(fixedDelay = 60_000)
  @Transactional
  public void expirarYCompletar() {
    LocalDateTime limite = LocalDateTime.now().minusMinutes(timeoutMin);
    for (Reserva r : reservas.findByEstado("PENDIENTE")) {
      if (r.creadoEn == null || !r.creadoEn.isBefore(limite)) continue;
      boolean pagada = pagos.findByReservaId(r.id).map(p -> "PAGADA".equals(p.estado)).orElse(false);
      if (!pagada) {
        r.estado = "CANCELADA";
        reservas.save(r);
        audit(r.id, "PENDIENTE", "CANCELADA:expiracion-sin-pago");
      }
    }
    // Compatibilidad: filas legacy PENDIENTE_PAGO → CANCELADA si vencidas
    for (Reserva r : reservas.findByEstado("PENDIENTE_PAGO")) {
      r.estado = "CANCELADA";
      reservas.save(r);
      audit(r.id, "PENDIENTE_PAGO", "CANCELADA:migracion-v3");
    }
    for (Reserva r : reservas.findByEstado("CONFIRMADA")) {
      if (r.fecha != null && r.fecha.isBefore(LocalDate.now())) {
        r.estado = "COMPLETADA";
        reservas.save(r);
        audit(r.id, "CONFIRMADA", "COMPLETADA");
      }
    }
    for (Reserva r : reservas.findByEstado("FINALIZADA")) {
      r.estado = "COMPLETADA";
      reservas.save(r);
      audit(r.id, "FINALIZADA", "COMPLETADA:migracion-v3");
    }
  }

  private void audit(Long id, String antes, String despues) {
    Auditoria a = new Auditoria();
    a.entidad = "reserva"; a.entidadId = id; a.accion = "job";
    a.estadoAntes = antes; a.estadoDespues = despues;
    auditoria.save(a);
  }
}
