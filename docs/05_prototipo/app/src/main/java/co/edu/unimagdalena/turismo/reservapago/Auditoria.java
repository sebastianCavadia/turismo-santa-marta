package co.edu.unimagdalena.turismo.reservapago;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria", schema = "reservas")
public class Auditoria {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  public String entidad;
  @Column(name = "entidad_id")
  public Long entidadId;
  public String accion;
  @Column(name = "estado_antes")
  public String estadoAntes;
  @Column(name = "estado_despues")
  public String estadoDespues;
  @Column(name = "usuario_id")
  public Long usuarioId;
  @Column(name = "creado_en")
  public LocalDateTime creadoEn = LocalDateTime.now();
}
