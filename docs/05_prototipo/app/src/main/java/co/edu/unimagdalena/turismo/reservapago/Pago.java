package co.edu.unimagdalena.turismo.reservapago;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago", schema = "reservas")
public class Pago {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  @Column(name = "reserva_id", unique = true)
  public Long reservaId;
  @Column(unique = true)
  public String reference;
  public String estado = "PAGADA";
  public String modo = "DEMO";
  @Column(name = "creado_en")
  public LocalDateTime creadoEn = LocalDateTime.now();
}
