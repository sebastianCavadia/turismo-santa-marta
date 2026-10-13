package co.edu.unimagdalena.turismo.catalogo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicio", schema = "catalogo")
public class Servicio {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  public String nombre;
  @Column(columnDefinition = "TEXT")
  public String descripcion;
  public String categoria;
  public String zona;
  @Column(name = "precio_cop")
  public Long precioCop;
  public String tipo;
  public Boolean habilitado;
  public Boolean validado = true; // RF095/RF085
  @Column(name = "prestador_id")
  public Long prestadorId;
  @Column(name = "creado_en")
  public LocalDateTime creadoEn;
}
