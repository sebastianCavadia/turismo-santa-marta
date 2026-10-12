package co.edu.unimagdalena.turismo.catalogo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "disponibilidad", schema = "catalogo",
  uniqueConstraints = @UniqueConstraint(columnNames = {"servicio_id","fecha"}))
public class Disponibilidad {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  @Column(name = "servicio_id")
  public Long servicioId;
  public LocalDate fecha;
  @Column(name = "cupo_total")
  public Integer cupoTotal;
  @Column(name = "cupo_ocupado")
  public Integer cupoOcupado;
  public Boolean bloqueado;
  public int cuposLibres() { return cupoTotal - cupoOcupado; }
}
