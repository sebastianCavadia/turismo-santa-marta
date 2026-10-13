package co.edu.unimagdalena.turismo.identity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion", schema = "identitys")
public class Notificacion {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  @Column(name = "destinatario_id")
  public Long destinatarioId;
  public String tipo;
  @Column(columnDefinition = "TEXT")
  public String mensaje;
  public Boolean leida = false;
  @Column(name = "creado_en")
  public LocalDateTime creadoEn = LocalDateTime.now();
}
