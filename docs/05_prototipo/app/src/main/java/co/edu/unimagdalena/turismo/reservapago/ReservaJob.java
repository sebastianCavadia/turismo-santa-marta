package co.edu.unimagdalena.turismo.reservapago;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Job demo: expiración/finalización (RF-054 simplificado).
// PENDIENTE_PAGO viejo → EXPIRADA (casi no ocurre con botón instantáneo, pero se deja).
// CONFIRMADA con fecha pasada → FINALIZADA (habilita opiniones en C-2).
@Component
public class ReservaJob {
  private final ReservaRepository reservas;
  private final AuditoriaRepository auditoria;
  private final int timeoutMin;

  public ReservaJob(ReservaRepository r, AuditoriaRepository a,
      @Value("${prototipo.pago-timeout-min:5}") int timeoutMin) {
    this.reservas = r; this.auditoria = a; this.timeoutMin = timeoutMin;
  }

  @Scheduled(fixedDelay = 60_000)
  @Transactional
  public void expirarYFinalizar() {
    LocalDateTime limite = LocalDateTime.now().minusMinutes(timeoutMin);
    for (Reserva r : reservas.findByEstado("PENDIENTE_PAGO")) {
      if (r.creadoEn != null && r.creadoEn.isBefore(limite)) {
        r.estado = "EXPIRADA";
        reservas.save(r);
        audit(r.id, "PENDIENTE_PAGO", "EXPIRADA");
      }
    }
    for (Reserva r : reservas.findByEstado("CONFIRMADA")) {
      if (r.fecha != null && r.fecha.isBefore(LocalDate.now())) {
        r.estado = "FINALIZADA";
        reservas.save(r);
        audit(r.id, "CONFIRMADA", "FINALIZADA");
      }
    }
  }

  private void audit(Long id, String antes, String despues) {
    Auditoria a = new Auditoria();
    a.entidad = "reserva"; a.entidadId = id; a.accion = "job";
    a.estadoAntes = antes; a.estadoDespues = despues;
    auditoria.save(a);
  }
}
