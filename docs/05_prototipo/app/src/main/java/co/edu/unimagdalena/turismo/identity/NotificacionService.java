package co.edu.unimagdalena.turismo.identity;

import org.springframework.stereotype.Service;
import java.util.List;

// Mock interno de notificaciones (correo real en C-2). RF037/039/096/100.
@Service
public class NotificacionService {
  private final NotificacionRepository repo;

  public NotificacionService(NotificacionRepository r) { this.repo = r; }

  public void notificar(Long destinatarioId, String tipo, String mensaje) {
    if (destinatarioId == null) return;
    Notificacion n = new Notificacion();
    n.destinatarioId = destinatarioId;
    n.tipo = tipo;
    n.mensaje = mensaje;
    repo.save(n);
  }

  public List<Notificacion> de(Long destinatarioId) {
    return repo.findByDestinatarioIdOrderByIdDesc(destinatarioId);
  }
}
