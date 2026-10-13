package co.edu.unimagdalena.turismo.catalogo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calificacion", schema = "catalogo")
public class Calificacion {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  @Column(name = "servicio_id")
  public Long servicioId;
  @Column(name = "turista_id")
  public Long turistaId;
  @Column(name = "reserva_id", unique = true)
  public Long reservaId;
  public Integer estrellas;
  @Column(columnDefinition = "TEXT")
  public String texto;
  @Column(name = "respuesta_prestador", columnDefinition = "TEXT")
  public String respuestaPrestador;
  @Column(name = "creado_en")
  public LocalDateTime creadoEn = LocalDateTime.now();
}
