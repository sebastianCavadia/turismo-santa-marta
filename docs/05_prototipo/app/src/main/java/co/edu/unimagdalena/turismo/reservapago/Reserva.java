package co.edu.unimagdalena.turismo.reservapago;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "reserva", schema = "reservas")
public class Reserva {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  @Column(name = "servicio_id")
  public Long servicioId;
  @Column(name = "turista_id")
  public Long turistaId;
  public LocalDate fecha;
  public LocalTime hora;
  public Integer personas;
  public String estado = "PENDIENTE_PAGO";
  @Column(name = "total_cop")
  public Long totalCop;
  @Column(name = "creado_en")
  public LocalDateTime creadoEn = LocalDateTime.now();
}
