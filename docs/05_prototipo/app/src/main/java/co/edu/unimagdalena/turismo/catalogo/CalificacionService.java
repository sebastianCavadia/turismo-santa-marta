package co.edu.unimagdalena.turismo.catalogo;

import co.edu.unimagdalena.turismo.reservapago.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

// RF075/076/080/081 + RF030: calificar 1-5 solo COMPLETADA 1x, promedio real, responder PRE.
@Service
public class CalificacionService {
  private final CalificacionRepository califs;
  private final ReservaRepository reservas;

  public CalificacionService(CalificacionRepository c, ReservaRepository r) {
    this.califs = c; this.reservas = r;
  }

  @Transactional
  public Calificacion calificar(Long servicioId, Long turistaId, Long reservaId, int estrellas, String texto) {
    if (estrellas < 1 || estrellas > 5) throw new IllegalArgumentException("Estrellas 1-5 (RF075)");
    var r = reservas.findById(reservaId).orElseThrow(() -> new IllegalArgumentException("Reserva no existe"));
    if (!r.servicioId.equals(servicioId)) throw new IllegalArgumentException("La reserva no es de ese servicio");
    if (!r.turistaId.equals(turistaId != null ? turistaId : 1L))
      throw new SecurityException("Solo quien reservó califica");
    if (!"COMPLETADA".equals(r.estado))
      throw new IllegalStateException("Solo con reserva COMPLETADA (RF076)");
    if (califs.existsByReservaId(reservaId))
      throw new IllegalStateException("Una calificación por reserva (RF076)");
    Calificacion c = new Calificacion();
    c.servicioId = servicioId;
    c.turistaId = r.turistaId;
    c.reservaId = reservaId;
    c.estrellas = estrellas;
    c.texto = texto;
    return califs.save(c);
  }

  @Transactional
  public Calificacion responder(Long califId, String rol, String texto) {
    if (!"PRE".equalsIgnoreCase(rol) && !"ADM".equalsIgnoreCase(rol))
      throw new SecurityException("Solo PRE responde (RF030)");
    var c = califs.findById(califId).orElseThrow(() -> new IllegalArgumentException("Calificación no existe"));
    c.respuestaPrestador = texto;
    return califs.save(c);
  }

  public Map<String, Object> resumen(Long servicioId, Integer estrellas) {
    var lista = estrellas == null
      ? califs.findByServicioIdOrderByIdDesc(servicioId)
      : califs.findByServicioIdAndEstrellasOrderByIdDesc(servicioId, estrellas);
    var agg = califs.promedioYTotal(servicioId).get(0);
    double prom = agg[0] == null ? 0 : ((Number) agg[0]).doubleValue();
    Map<String, Object> out = new LinkedHashMap<>();
    out.put("promedio", Math.round(prom * 10) / 10.0);
    out.put("total", ((Number) agg[1]).longValue());
    out.put("lista", lista);
    return out;
  }

  public double promedio(Long servicioId) {
    var agg = califs.promedioYTotal(servicioId).get(0);
    double prom = agg[0] == null ? 0 : ((Number) agg[0]).doubleValue();
    return Math.round(prom * 10) / 10.0;
  }

  public long total(Long servicioId) {
    return ((Number) califs.promedioYTotal(servicioId).get(0)[1]).longValue();
  }
}
